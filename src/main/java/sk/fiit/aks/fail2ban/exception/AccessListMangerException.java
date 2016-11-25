package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AccessListMangerException extends Exception {

    public AccessListMangerException(String message) {
        super(message);
    }

    public AccessListMangerException(String message, Throwable cause) {
        super(message, cause);
    }
}
