package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.element.NetworkElement;
import java.util.HashMap;
import java.util.Map;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.exception.LoginManagerException;
import sk.fiit.aks.fail2ban.manager.LoginManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginManagerImpl implements LoginManager {

    private final NetworkElement element;
    private final Map<String, Integer> failedRecordsMap = new HashMap<>();

    public LoginManagerImpl(NetworkElement networkElement) {
        this.element = networkElement;
    }

    @Override
    public void addFailedRecord(String ipAddress) throws LoginManagerException {
        if (failedRecordsMap.get(ipAddress) == null) {
            System.out.println("Initializing failed login record for ipaddress " + ipAddress);
            failedRecordsMap.put(ipAddress, 1);
        } else {
            failedRecordsMap.put(ipAddress, failedRecordsMap.get(ipAddress) + 1);
            System.out.println("Increasing value for " + ipAddress + ". Current count is " + failedRecordsMap.get(ipAddress));
            if (shouldBeBanned(failedRecordsMap.get(ipAddress))) {
                System.out.println("BANNING ADDRESS " + ipAddress);
                banAddressForAllRouters(ipAddress);
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
                router.getAccessListManager().addAceToAccessList(router.getAccessListManager().createBlockingAce(ipAddress));
            } catch (AccessListManagerException ex) {
                throw new LoginManagerException("Unable to ban address for router " + router.getId(), ex);
            }
        }
    }

    private boolean shouldBeBanned(int failedCount) {
        return failedCount > 2;
    }
}
