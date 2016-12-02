package sk.fiit.aks.fail2ban.test;

import com.cisco.onep.interfaces.NetworkInterface;
import com.cisco.onep.policy.L3Ace;
import org.junit.Test;
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

        try {
            ElementRegistry.getInstance().registerRouter("192.168.132.5", "kokot", "kokot", "csr1");
            Router router = ElementRegistry.getInstance().getAllRouters().get(0);
            L3Ace ace = router.getAccessListManager().createBlockingAce("192.168.132.6");
            router.getAccessListManager().addAceToAccessList(ace);

            for (NetworkInterface iface : router.getInterfaceManager().getAllInterfaces()) {
                System.out.println("Applying acl to interface " + iface.getName());
                router.getAccessListManager().applyAclToInterface(iface);
            }
            
            Thread.sleep(10000);
            System.out.println("ACL successfully applied");
        } catch (Fail2banConnectionException ex) {
            System.out.println("Application terminated " + ex.getMessage());
        }
    }

}
