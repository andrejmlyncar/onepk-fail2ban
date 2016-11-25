package sk.fiit.aks.fail2ban.controller;

import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.element.SessionHandle;
import sk.fiit.aks.fail2ban.manager.AccessListManager;
import sk.fiit.aks.fail2ban.manager.InterfaceManager;
import sk.fiit.aks.fail2ban.manager.impl.AccessLIstManagerImpl;
import sk.fiit.aks.fail2ban.manager.impl.InterfaceManagerImpl;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ElementManagerAccessor {

    private final NetworkElement networkElement;
    private final SessionHandle sessionHandle;

    private InterfaceManager interfaceManager;
    private AccessListManager accessListManager;

    public ElementManagerAccessor(NetworkElement networkElement) {
        this.sessionHandle = networkElement.getSessionHandle();
        this.networkElement = networkElement;
    }

    public ElementManagerAccessor(SessionHandle sessionHandle) {
        this.sessionHandle = sessionHandle;
        this.networkElement = sessionHandle.getNetworkElement();
    }

    public InterfaceManager getInterfaceManager() {
        if (this.interfaceManager == null) {
            this.interfaceManager = new InterfaceManagerImpl(networkElement);
        }
        return this.interfaceManager;
    }

    public AccessListManager getAccessListManager() {
        if (this.accessListManager == null) {
            this.accessListManager = new AccessLIstManagerImpl(networkElement);
        }
        return this.accessListManager;
    }
}
