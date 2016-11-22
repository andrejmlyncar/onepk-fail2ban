package sk.fiit.aks.fail2ban.exception;

public class Fail2banConnectionException extends Exception {

    public Fail2banConnectionException(String message) {
        super(message);
    }

    public Fail2banConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
