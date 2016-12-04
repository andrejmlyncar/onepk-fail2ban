/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fiit.aks.fail2ban.test;

import com.cisco.onep.core.exception.OnepConnectionException;
import com.cisco.onep.core.exception.OnepRemoteProcedureException;
import com.cisco.onep.element.SessionHandle;
import com.cisco.onep.interfaces.NetworkInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import sk.fiit.aks.fail2ban.controller.AclTimeoutManager;
import sk.fiit.aks.fail2ban.controller.ConnectionFactory;
import sk.fiit.aks.fail2ban.manager.impl.InterfaceManagerImpl;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;
import sk.fiit.aks.fail2ban.exception.InterfaceManagerException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ConnectionTest {

    @Test
    public void basicConnectionTest() throws OnepRemoteProcedureException, OnepConnectionException, InterfaceManagerException {
        Logger logger = Logger.getLogger(AclTimeoutManager.class.getName());
        ConnectionFactory factory = new ConnectionFactory();
        SessionHandle handle;
        try {
            handle = factory.createConnection("cisco", "cisco", "192.168.132.5");
            InterfaceManagerImpl interfaceManager = new InterfaceManagerImpl(handle.getNetworkElement());
            List<NetworkInterface> interfaces = interfaceManager.getAllInterfaces();
            for (NetworkInterface interf : interfaces) {
                logger.log(Level.INFO, interf.getName());
                logger.log(Level.INFO, interf.getAddressList().get(0).getHostAddress());
            }

        } catch (Fail2banConnectionException ex) {
            logger.log(Level.SEVERE, "Application terminated: {0}.", new Object[]{ex.getMessage()});
        }
    }
}
