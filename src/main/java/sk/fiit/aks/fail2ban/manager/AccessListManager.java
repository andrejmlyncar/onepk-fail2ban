package sk.fiit.aks.fail2ban.manager;

import com.cisco.onep.interfaces.NetworkInterface;
import com.cisco.onep.policy.L3Ace;
import com.cisco.onep.policy.L3Acl;
import sk.fiit.aks.fail2ban.exception.AccessListMangerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface AccessListManager {
    
    public L3Acl createAccessList() throws AccessListMangerException;
    
    public L3Ace createBlockingAce(String ipAddress) throws AccessListMangerException;
    
    public void addAceToAccessList(L3Acl acl, L3Ace ace);
    
    public void applyAclToInterface(L3Acl acl, NetworkInterface networkInterface);
    
}
