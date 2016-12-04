package sk.fiit.aks.fail2ban.test;

import com.cisco.onep.interfaces.NetworkInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sk.fiit.aks.fail2ban.controller.AclTimeoutManager;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;
import sk.fiit.aks.fail2ban.exception.InterfaceManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AclTest {

    @Test
    public void aclTest() throws AccessListManagerException, InterfaceManagerException, InterruptedException {
        
        Logger logger = Logger.getLogger(AclTimeoutManager.class.getName());
        
        try {
            ElementRegistry.getInstance().registerRouter("192.168.132.5", "kokot", "kokot", "csr1");
            Router router = ElementRegistry.getInstance().getAllRouters().get(0);
            router.getAccessListManager().createBlockingAce("192.168.132.6");
            for (NetworkInterface iface : router.getInterfaceManager().getAllInterfaces()) {
                logger.log(Level.INFO, "Applying acl to interface {0}", new Object[]{iface.getName()});
                router.getAccessListManager().applyAclToInterface(iface);
            }
            
            Thread.sleep(10000);
            logger.log(Level.INFO, "ACL successfully applied");
        } catch (Fail2banConnectionException ex) {
            logger.log(Level.SEVERE, "Application terminated: {0}", new Object[]{ex.getMessage()});
        }
    }

}
