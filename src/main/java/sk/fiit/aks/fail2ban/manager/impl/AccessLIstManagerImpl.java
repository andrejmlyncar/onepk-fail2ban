package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.interfaces.NetworkInterface;
import com.cisco.onep.policy.L3Ace;
import com.cisco.onep.policy.L3Acl;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.manager.AccessListManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AccessLIstManagerImpl implements AccessListManager {

    private final NetworkElement element;
    
    public AccessLIstManagerImpl(NetworkElement element) {
        this.element = element;
    }
    
    @Override
    public L3Acl createAccessList() throws AccessListManagerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public L3Ace createBlockingAce(String ipAddress) throws AccessListManagerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addAceToAccessList(L3Acl acl, L3Ace ace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyAclToInterface(L3Acl acl, NetworkInterface networkInterface) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getAccessList() throws AccessListManagerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
