package sk.fiit.aks.fail2ban.manager;

import java.util.List;
import sk.fiit.aks.fail2ban.exception.LoggingManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface LoggingManager {

    public void getLoggingMessages() throws LoggingManagerException;

    public List<String> getFailedLoggingRecord() throws LoggingManagerException;

}
