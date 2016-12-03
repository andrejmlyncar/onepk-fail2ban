package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginManagerException extends Exception {

    public LoginManagerException(String message) {
        super(message);
    }

    public LoginManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
