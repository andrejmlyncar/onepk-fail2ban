package sk.fiit.aks.fail2ban.controller.listener;

import com.cisco.onep.element.SyslogEvent;
import com.cisco.onep.element.SyslogListener;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginSysLogListener implements SyslogListener {

    @Override
    public void handleEvent(SyslogEvent se, Object o) {
        System.out.println("Received syslog event " + se.getMessage() + " END OF MESSAGE");
        System.out.println("Syslog object " + o);
    }
    
}
