package sk.fiit.aks.fail2ban.controller;

import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.interfaces.InterfaceFilter;
import com.cisco.onep.interfaces.NetworkInterface;
import java.util.List;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class InterfaceManager {

    public List<NetworkInterface> getAllInterfaces(NetworkElement element) throws Fail2banConnectionException {
        List<NetworkInterface> interfaceList = null;
        try {
            interfaceList = element.getInterfaceList(new InterfaceFilter());
        } catch (Exception e) {
            throw new Fail2banConnectionException("Unable to obtain interface list", e);
        }
        return interfaceList;
    }
}
