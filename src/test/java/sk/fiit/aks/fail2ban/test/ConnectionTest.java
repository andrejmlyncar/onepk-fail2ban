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
import org.junit.Test;
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
        ConnectionFactory factory = new ConnectionFactory();
        SessionHandle handle;
        try {
            handle = factory.createConnection("cisco", "cisco", "192.168.132.5");
            InterfaceManagerImpl interfaceManager = new InterfaceManagerImpl(handle.getNetworkElement());
            List<NetworkInterface> interfaces = interfaceManager.getAllInterfaces();
            for (NetworkInterface interf : interfaces) {
                System.out.println(interf.getName());
                System.out.println(interf.getAddressList().get(0).getHostAddress());
            }

        } catch (Fail2banConnectionException ex) {
            System.out.println("Application terminated " + ex.getMessage());
        }
    }
}
