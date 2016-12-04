package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.core.exception.OnepConnectionException;
import com.cisco.onep.core.exception.OnepIllegalArgumentException;
import com.cisco.onep.core.exception.OnepRemoteProcedureException;
import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.interfaces.InterfaceFilter;
import com.cisco.onep.interfaces.NetworkInterface;
import java.util.List;
import sk.fiit.aks.fail2ban.exception.InterfaceManagerException;
import sk.fiit.aks.fail2ban.manager.InterfaceManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class InterfaceManagerImpl implements InterfaceManager {

    private final NetworkElement element;

    public InterfaceManagerImpl(NetworkElement element) {
        this.element = element;
    }

    @Override
    public List<NetworkInterface> getAllInterfaces() throws InterfaceManagerException {
        List<NetworkInterface> interfaceList = null;
        try {
            interfaceList = element.getInterfaceList(new InterfaceFilter());
        } catch (OnepConnectionException | OnepIllegalArgumentException | OnepRemoteProcedureException e) {
            throw new InterfaceManagerException("Unable to obtain interface list.", e);
        }
        if (interfaceList == null) {
            throw new InterfaceManagerException("Interface List was not successfully initailzed.");
        }

        return interfaceList;
    }

    @Override
    public NetworkInterface getInterface(String name) throws InterfaceManagerException {
        try {
            return element.getInterfaceByName(name);
        } catch (OnepIllegalArgumentException | OnepRemoteProcedureException | OnepConnectionException ex) {
            throw new InterfaceManagerException("Unable to obtain interface " + name, ex);
        }
    }
}
