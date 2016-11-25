package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AccessListManagerException extends Exception {

    public AccessListManagerException(String message) {
        super(message);
    }

    public AccessListManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
