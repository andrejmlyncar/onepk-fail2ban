package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.element.NetworkElement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.fiit.aks.fail2ban.controller.AclTimeoutManager;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.entity.Router;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.exception.LoginManagerException;
import sk.fiit.aks.fail2ban.manager.LoginManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginManagerImpl implements LoginManager {

    private final Logger logger;
    private final NetworkElement element;
    private final Map<String, Integer> failedRecordsMap = new HashMap<>();

    public LoginManagerImpl(NetworkElement networkElement) {
        this.logger = Logger.getLogger(AclTimeoutManager.class.getName());
        this.element = networkElement;
    }

    @Override
    public void addFailedRecord(String ipAddress) throws LoginManagerException {
        if (failedRecordsMap.get(ipAddress) == null) {
            logger.log(Level.INFO, "Initializing failed login record for ipaddress {0}.", new Object[]{ipAddress});
            failedRecordsMap.put(ipAddress, 1);
        } else {
            failedRecordsMap.put(ipAddress, failedRecordsMap.get(ipAddress) + 1);
             logger.log(Level.INFO, "Increasing value for {0}. Current count is {1}.", new Object[]{ipAddress, failedRecordsMap.get(ipAddress)});
            if (shouldBeBanned(failedRecordsMap.get(ipAddress))) {
                banAddressForAllRouters(ipAddress);
                failedRecordsMap.put(ipAddress, failedRecordsMap.get(ipAddress) - 2);
            }
        }
    }

    @Override
    public int getFailedAttemptsForIp(String ipAddress) throws LoginManagerException {
        Integer value = failedRecordsMap.get(ipAddress);
        if (value == null) {
            throw new LoginManagerException("Unable to get failed attempts for ip address " + ipAddress);
        }
        return failedRecordsMap.get(ipAddress);
    }

    private void banAddressForAllRouters(String ipAddress) throws LoginManagerException {
        for (Router router : ElementRegistry.getInstance().getAllRouters()) {
            try {
                if (router.getAccessListManager().getBannedRecord(ipAddress) == null) {
                    logger.log(Level.INFO, "BANNING ADDRESS {0} for router {1}.", new Object[]{ipAddress, router.getName()});
                    router.getAccessListManager().createBlockingAce(ipAddress);
                } else {
                     logger.log(Level.INFO, "Extending banned time for ip address {0} for router {1}.", new Object[]{ipAddress, router.getName()});
                    router.getAccessListManager().getBannedRecord(ipAddress).extendBannedTime();
                }
            } catch (AccessListManagerException ex) {
                throw new LoginManagerException("Unable to ban address for router " + router.getId(), ex);
            }
        }
    }

    private boolean shouldBeBanned(int failedCount) {
        return failedCount > 2;
    }
}
