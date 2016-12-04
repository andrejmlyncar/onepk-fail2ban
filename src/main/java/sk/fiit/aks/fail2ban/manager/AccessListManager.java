package sk.fiit.aks.fail2ban.manager;

import com.cisco.onep.interfaces.NetworkInterface;
import com.cisco.onep.policy.L3Ace;
import com.cisco.onep.policy.L3Acl;
import java.util.List;
import sk.fiit.aks.fail2ban.enitiy.BannedRecord;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface AccessListManager {
    
    public void createBlockingAce(String ipAddress) throws AccessListManagerException;
    
    public void applyAclToInterface(NetworkInterface networkInterface);
    
    public L3Acl getAccessList() throws AccessListManagerException;
    
    public void removeAce(int sequenceNumber) throws AccessListManagerException;
    
    public void removeAce(L3Ace ace) throws AccessListManagerException;
    
    public List<BannedRecord> getBannedRecords();
    
    public BannedRecord getBannedRecord(String ipAddress);
}
