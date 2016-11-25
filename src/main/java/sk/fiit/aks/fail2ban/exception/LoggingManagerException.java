package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoggingManagerException extends Exception {

    public LoggingManagerException(String message) {
        super(message);
    }

    public LoggingManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
