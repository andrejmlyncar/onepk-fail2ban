package sk.fiit.aks.fail2ban.controller;

import com.cisco.onep.core.exception.OnepConnectionException;
import com.cisco.onep.core.exception.OnepDuplicateElementException;
import com.cisco.onep.core.exception.OnepIllegalArgumentException;
import com.cisco.onep.core.exception.OnepInvalidSettingsException;
import com.cisco.onep.core.util.TLSUnverifiedElementHandler;
import com.cisco.onep.element.NetworkApplication;
import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.element.SessionConfig;
import com.cisco.onep.element.SessionHandle;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ConnectionFactory {

    private final List<NetworkElement> elements = new ArrayList<NetworkElement>();

    public ConnectionFactory() {
    }

    public SessionHandle createConnection(String username, String password, String ipAddress, String name) throws Fail2banConnectionException {
        try {
            NetworkApplication networkApplication = NetworkApplication.getInstance();
            NetworkElement networkElement = networkApplication.getNetworkElement(ipAddress);
            networkApplication.setName(name);
            SessionHandle handle = networkElement.connect(username, password, createSessionConfig());
            elements.add(networkElement);
            return handle;
        } catch (OnepConnectionException ex) {
            throw new Fail2banConnectionException("Unable to create connection with network element", ex);
        } catch (OnepIllegalArgumentException ex) {
            throw new Fail2banConnectionException("Unable to create connection with network element", ex);
        } catch (OnepDuplicateElementException ex) {
            throw new Fail2banConnectionException("Unable to create connection with network element", ex);
        } catch (OnepInvalidSettingsException ex) {
            throw new Fail2banConnectionException("Unable to create connection with network element", ex);
        } catch (UnknownHostException ex) {
            throw new Fail2banConnectionException("Unable to create connection with network element", ex);
        }
    }

    public SessionHandle getConnection(String name) throws Fail2banConnectionException {
        for (NetworkElement element : elements) {
            if (element.getAppname().equals(name)) {
                return element.getSessionHandle();
            }
        }
        throw new Fail2banConnectionException("Unable to obtain connection with name " + name + " connection was not initialized");
    }

    private SessionConfig createSessionConfig() {
        SessionConfig config = null;
        config = new SessionConfig(SessionConfig.SessionTransportMode.TLS);
        config.setPort(SessionConfig.DEFAULT_PORT);
        config.setReconnectTimer(10);
        config.setEventQueueSize(100);
        config.setEventThreadPool(25);
        config.setReconnectTimer(25);
        config.setEventDropMode(SessionConfig.DEFAULT_EVENT_DROP_MODE);
        config.setKeepAliveIdleTime(SessionConfig.DEFAULT_KEEPALIVE_IDLE_TIME);
        config.setKeepAliveInterval(SessionConfig.DEFAULT_KEEPALIVE_INTERVAL);
        config.setKeepAliveRetryCount(SessionConfig.DEFAULT_KEEPALIVE_RETRY_COUNT);
        config.setTLSPinning("C:\\Users\\Andrej\\Desktop\\cacert2\\pinfile", new PinningHandler());
        return config;
    }

    public class PinningHandler implements TLSUnverifiedElementHandler {

        @Override
        public TLSUnverifiedElementHandler.Decision handleVerify(String host, String hashType, String fingerprint, boolean changed) {
            return TLSUnverifiedElementHandler.Decision.ACCEPT_AND_PIN;
        }
    }
}
