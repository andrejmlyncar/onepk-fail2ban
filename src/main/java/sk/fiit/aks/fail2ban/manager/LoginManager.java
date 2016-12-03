package sk.fiit.aks.fail2ban.manager;

import sk.fiit.aks.fail2ban.exception.LoginManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface LoginManager {

    public void addFailedRecord(String ipAddress) throws LoginManagerException;

    public int getFailedAttemptsForIp(String ipAddress) throws LoginManagerException;

}
