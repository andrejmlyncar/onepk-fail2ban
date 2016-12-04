package sk.fiit.aks.fail2ban.controller;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.fiit.aks.fail2ban.enitiy.BannedRecord;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AclTimeoutManager extends Thread {

    private final Router router;

    public AclTimeoutManager(Router router) {
        super();
        this.router = router;
    }

    @Override
    public void run() {
        while (isAlive()) {
            System.out.println("Starting to check ACL timeouts for router " + router.getName());
            try {
                Iterator<BannedRecord> recordIterator = router.getAccessListManager().getBannedRecords().iterator();
                while (recordIterator.hasNext()) {
                    BannedRecord record = recordIterator.next();
                    if (record.getExpireTimestamp().getTime() < System.currentTimeMillis()) {
                        System.out.println("Expired ACL found: Removing acl with seq num " + record.getAce().getSequence() + " from router " + router.getName());
                        router.getAccessListManager().removeAce(record.getAce());
                        recordIterator.remove();
                    }
                }
            } catch (AccessListManagerException ex) {
                Logger.getLogger(AclTimeoutManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Check completed. Thread put into sleeping state");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println("Error during thread sleep execution of AclTimeoutManager:" + ex.getMessage());
            }
        }
    }
}
