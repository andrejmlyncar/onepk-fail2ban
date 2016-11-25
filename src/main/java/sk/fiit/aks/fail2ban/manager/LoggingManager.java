package sk.fiit.aks.fail2ban.manager;

import sk.fiit.aks.fail2ban.exception.LoggingManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface LoggingManager {

    public void getLoggingMessages() throws LoggingManagerException;
}
