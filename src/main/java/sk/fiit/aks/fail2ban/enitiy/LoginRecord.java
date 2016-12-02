package sk.fiit.aks.fail2ban.enitiy;

import java.sql.Timestamp;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoginRecord {
    
    private final Timestamp loginTimestamp;
    private final String ipAddress;
    private final Router router;
    
    
    public LoginRecord(String nonParsedRecord, Router router) {
        this.loginTimestamp = new Timestamp(2313213213L);
        this.ipAddress = nonParsedRecord.substring(0,23);
        this.router = router;
    }
    
    public Timestamp getLoginTimestamp() {
        return this.loginTimestamp;
    }
    
    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public Router getRouter() {
        return this.router;
    }
}
