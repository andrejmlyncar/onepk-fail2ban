/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fiit.aks.fail2ban.test;

import com.cisco.onep.element.SessionHandle;
import org.junit.Test;
import sk.fiit.aks.fail2ban.controller.ConnectionFactory;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ConnectionTest {

    @Test
    public void basicConnectionTest() {
        ConnectionFactory factory = new ConnectionFactory();
        SessionHandle handle;
        try {
            handle = factory.createConnection("kokot", "kokot", "192.168.132.5", "csr_router1");
        } catch (Fail2banConnectionException ex) {
            System.out.println("Application terminated " + ex.getMessage());
        }
    }
}
