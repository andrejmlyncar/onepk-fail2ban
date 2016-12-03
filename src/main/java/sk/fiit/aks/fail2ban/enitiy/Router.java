package sk.fiit.aks.fail2ban.enitiy;

import com.cisco.onep.core.exception.OnepException;
import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.element.SessionHandle;
import com.cisco.onep.element.SyslogFilter;
import java.util.UUID;
import sk.fiit.aks.fail2ban.controller.listener.LoginSysLogListener;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;
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

    public Router(NetworkElement networkElement, String routerName) throws Fail2banConnectionException {
        this.sessionHandle = networkElement.getSessionHandle();
        this.networkElement = networkElement;
        this.id = UUID.randomUUID().toString();
        this.name = routerName;
        try {
            this.networkElement.addSyslogListener(new LoginSysLogListener(), new SyslogFilter("Login Authentication Failed"), 8);
        } catch (OnepException ex) {
            throw new Fail2banConnectionException("Unable to setup logging listener for router" + name, ex);
        }
    }

    public Router(SessionHandle sessionHandle, String routerName) throws Fail2banConnectionException {
        this.sessionHandle = sessionHandle;
        this.networkElement = sessionHandle.getNetworkElement();
        this.id = UUID.randomUUID().toString();
        this.name = routerName;
        try {
            this.networkElement.addSyslogListener(new LoginSysLogListener(), new SyslogFilter("Login Authentication Failed"), 8);
        } catch (OnepException ex) {
            throw new Fail2banConnectionException("Unable to setup logging listener for router" + name, ex);
        }
    }

    public InterfaceManager getInterfaceManager() {
        if (this.interfaceManager == null) {
            this.interfaceManager = new InterfaceManagerImpl(networkElement);
        }
        return this.interfaceManager;
    }

    public AccessListManager getAccessListManager() throws AccessListManagerException {
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
