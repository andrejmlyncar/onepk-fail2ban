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

    private final Logger logger;
    private final Router router;
 
    public AclTimeoutManager(Router router) {
        super();
        this.logger = Logger.getLogger(AclTimeoutManager.class.getName());
        this.router = router;
    }

    @Override
    public void run() {
        while (isAlive()) {
            logger.log(Level.INFO, "Starting to check ACL timeouts for router {0}.", new Object[]{router.getName()});
            try {
                Iterator<BannedRecord> recordIterator = router.getAccessListManager().getBannedRecords().iterator();
                while (recordIterator.hasNext()) {
                    BannedRecord record = recordIterator.next();
                    if (record.getExpireTimestamp().getTime() < System.currentTimeMillis()) {
                        logger.log(Level.INFO, "Expired ACL found: Removing acl with seq num {0} from router {1}.", new Object[]{record.getAce().getSequence(), router.getName()});
                        router.getAccessListManager().removeAce(record.getAce());
                        recordIterator.remove();
                    }
                }
            } catch (AccessListManagerException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            
            logger.log(Level.INFO, "Check completed. Thread put into sleeping state.");
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, "Error during thread sleep execution of AclTimeoutManager: {0}.", new Object[]{ex.getMessage()});
            }
        }
    }
}
