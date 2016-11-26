package sk.fiit.aks.fail2ban.enitiy;

import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.element.SessionHandle;
import java.util.UUID;
import sk.fiit.aks.fail2ban.manager.AccessListManager;
import sk.fiit.aks.fail2ban.manager.InterfaceManager;
import sk.fiit.aks.fail2ban.manager.LoggingManager;
import sk.fiit.aks.fail2ban.manager.impl.AccessLIstManagerImpl;
import sk.fiit.aks.fail2ban.manager.impl.InterfaceManagerImpl;
import sk.fiit.aks.fail2ban.manager.impl.LoggingManagerImpl;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class Router {

    private final NetworkElement networkElement;
    private final SessionHandle sessionHandle;
    private final String id;
    private final String name;

    private InterfaceManager interfaceManager;
    private AccessListManager accessListManager;
    private LoggingManager loggingManager;

    public Router(NetworkElement networkElement, String routerName) {
        this.sessionHandle = networkElement.getSessionHandle();
        this.networkElement = networkElement;
        this.id = UUID.randomUUID().toString();
        this.name = routerName;
    }

    public Router(SessionHandle sessionHandle, String routerName) {
        this.sessionHandle = sessionHandle;
        this.networkElement = sessionHandle.getNetworkElement();
        this.id = UUID.randomUUID().toString();
        this.name = routerName;
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

    public LoggingManager getLoggingManager() {
        if (this.loggingManager == null) {
            this.loggingManager = new LoggingManagerImpl(networkElement);
        }
        return this.loggingManager;
    }

    public String getId() {
        return this.id;
    }

    public String getAddress() {
        return this.networkElement.getAddress().getHostAddress();
    }

    public String getName() {
        return this.name;
    }
}
