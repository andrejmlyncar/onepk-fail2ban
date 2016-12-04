package sk.fiit.aks.fail2ban.controller.listener;

import com.cisco.onep.element.SyslogEvent;
import com.cisco.onep.element.SyslogListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import sk.fiit.aks.fail2ban.controller.AclTimeoutManager;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.LoginManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginSysLogListener implements SyslogListener {

    private final Logger logger;
    private final Router router;

    public LoginSysLogListener(Router router) {
        this.logger = Logger.getLogger(AclTimeoutManager.class.getName());
        this.router = router;
    }

    @Override
    public void handleEvent(SyslogEvent se, Object o) {
        //    String record = "*Dec  3 14:35:19.629: %SEC_LOGIN-4-LOGIN_FAILED: Login failed [user: root] [Source: 192.168.132.2] [localport: 22] [Reason: Login Authentication Failed] at 14:35:19 UTC Sat Dec 3 2016 END OF MESSAGE";
        String failedRecordString = se.getMessage();
        String ipAddress = StringUtils.substringBetween(failedRecordString, "[Source: ", "]");
        if (failedRecordString.contains("Login Authentication Failed")) {
            logger.log(Level.WARNING, "Failed login attempt from {0}.", new Object[]{ipAddress});
            try {
                this.router.getLoginManager().addFailedRecord(ipAddress);
            } catch (LoginManagerException ex) {
                logger.log(Level.WARNING, "Failed to handle login event {0}.", new Object[]{ex.getMessage()});
            }
        }
    }
}
