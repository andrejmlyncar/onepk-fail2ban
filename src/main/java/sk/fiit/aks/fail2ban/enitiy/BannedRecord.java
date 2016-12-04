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
    
    public BannedRecord(L3Ace ace) {
        this.ace =ace;
        this.expireTimestamp = new Timestamp(System.currentTimeMillis() + 1 * 30 * 1000);
    }
    
    public Timestamp getExpireTimestamp() {
        return this.expireTimestamp;
    }
    
    public L3Ace getAce() {
        return this.ace;
    }
}
