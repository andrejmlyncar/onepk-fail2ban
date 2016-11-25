package sk.fiit.aks.fail2ban.manager;

import com.cisco.onep.interfaces.NetworkInterface;
import java.util.List;
import sk.fiit.aks.fail2ban.exception.InterfaceManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public interface InterfaceManager {
    
    public NetworkInterface getInterface(String name) throws InterfaceManagerException;
    
    public List<NetworkInterface> getAllInterfaces() throws InterfaceManagerException;
}
