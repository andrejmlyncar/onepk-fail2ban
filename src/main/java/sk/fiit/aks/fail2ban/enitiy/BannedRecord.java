package sk.fiit.aks.fail2ban.enitiy;

import com.cisco.onep.policy.L3Ace;
import java.sql.Timestamp;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class BannedRecord {

    private final L3Ace ace;
    private final Timestamp expireTimestamp;
    private final String ipAddress;

    public BannedRecord(L3Ace ace, String ipAddress) {
        this.ace = ace;
        this.ipAddress = ipAddress;
        this.expireTimestamp = new Timestamp(System.currentTimeMillis() + 1 * 60 * 1000);
    }

    public Timestamp getExpireTimestamp() {
        return this.expireTimestamp;
    }

    public L3Ace getAce() {
        return this.ace;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void extendBannedTime() {
        this.expireTimestamp.setTime(expireTimestamp.getTime() + 1 * 60 * 1000);
    }
}
