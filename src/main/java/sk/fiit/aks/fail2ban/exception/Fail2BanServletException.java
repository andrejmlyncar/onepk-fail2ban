package sk.fiit.aks.fail2ban.exception;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class Fail2BanServletException extends Exception {

    public Fail2BanServletException(String message) {
        super(message);
    }

    public Fail2BanServletException(String message, Throwable cause) {
        super(message, cause);
    }
}
