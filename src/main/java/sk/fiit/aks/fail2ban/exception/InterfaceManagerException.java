package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class InterfaceManagerException extends Exception {

    public InterfaceManagerException(String message) {
        super(message);
    }

    public InterfaceManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
