package sk.fiit.aks.fail2ban.test;

import org.junit.Test;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;
import sk.fiit.aks.fail2ban.exception.LoggingManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoggingTest {

    @Test
    public void loggingTest() throws LoggingManagerException, Fail2banConnectionException {
        ElementRegistry.getInstance().registerRouter("192.168.132.5", "kokot", "kokot", "csr1");
        Router router = ElementRegistry.getInstance().getAllRouters().get(0);
        router.getLoggingManager().getLoggingMessages();
    }
}
