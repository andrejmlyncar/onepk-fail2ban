package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.core.exception.OnepIllegalArgumentException;
import com.cisco.onep.core.util.OnepConstants.OnepAddressFamilyType;
import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.interfaces.NetworkInterface;
import com.cisco.onep.policy.Acl;
import com.cisco.onep.policy.L3Ace;
import com.cisco.onep.policy.L3Acl;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import sk.fiit.aks.fail2ban.enitiy.BannedRecord;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.manager.AccessListManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class AccessLIstManagerImpl implements AccessListManager {

    private final NetworkElement element;
    private final L3Acl accessList;
    private int sequenceNumber = 1;
    private final List<BannedRecord> bannedRecords = new ArrayList<>();

    public AccessLIstManagerImpl(NetworkElement element) throws AccessListManagerException {
        this.element = element;
        try {
            this.accessList = new L3Acl(element, OnepAddressFamilyType.ONEP_AF_INET);
            accessList.setLifeTime(Acl.OnepLifeTime.ONEP_TRANSIENT);
            accessList.addAce(createAllowAllAce());
        } catch (OnepIllegalArgumentException ex) {
            throw new AccessListManagerException("Unable to create acl ", ex);
        }
    }

    @Override
    public L3Ace createBlockingAce(String ipAddress) throws AccessListManagerException {
        L3Ace ace = new L3Ace(this.sequenceNumber++, false);
        ace.setLogFlag(L3Ace.LogFlag.ONEP_ACL_LOG_NORMAL);
        try {
            ace.setSrcPrefix(InetAddress.getByName(ipAddress), (short) 32);
            ace.setDstPrefix(this.element.getAddress(), (short) 32);
        } catch (UnknownHostException ex) {
            throw new AccessListManagerException("Unknown ip address", ex);
        } catch (OnepIllegalArgumentException ex) {
            throw new AccessListManagerException("Failed to create ace", ex);
        }
        return ace;
    }

    @Override
    public void addAceToAccessList(L3Ace ace) throws AccessListManagerException {
        try {
            this.accessList.addAce(ace);
            this.bannedRecords.add(new BannedRecord(ace));
        } catch (OnepIllegalArgumentException ex) {
            throw new AccessListManagerException("Unable to add ace to acl", ex);
        }
    }

    @Override
    public void applyAclToInterface(NetworkInterface networkInterface) {
        this.accessList.applyToInterface(networkInterface, Acl.Direction.ONEP_DIRECTION_IN);
    }

    @Override
    public L3Acl getAccessList() throws AccessListManagerException {
        return this.accessList;
    }

    private L3Ace createAllowAllAce() {
        L3Ace ace = new L3Ace(999, true);
        ace.setLogFlag(L3Ace.LogFlag.ONEP_ACL_LOG_NORMAL);
        ace.setSrcPrefixAny();
        ace.setDstPrefixAny();
        return ace;
    }

    @Override
    public void removeAce(int sequenceNumber) throws AccessListManagerException {
        for (L3Ace ace : accessList.getAceList()) {
            if (ace.getSequence() == sequenceNumber) {
                try {
                    this.accessList.removeAce(ace);
                    return;
                } catch (OnepIllegalArgumentException ex) {
                    throw new AccessListManagerException("Unable to remove ace from acl", ex);
                }
            }
        }
        throw new AccessListManagerException("Ace to remove not found: " + sequenceNumber);
    }

    @Override
    public void removeAce(L3Ace ace) throws AccessListManagerException {
        try {
            this.accessList.removeAce(ace);
        } catch (OnepIllegalArgumentException ex) {
            throw new AccessListManagerException("Unable to remove ace from acl", ex);
        }
    }

    @Override
    public List<BannedRecord> getBannedRecords() {
        return this.bannedRecords;
    }
}
