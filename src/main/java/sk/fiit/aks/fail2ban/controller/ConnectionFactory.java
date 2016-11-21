package sk.fiit.aks.fail2ban.controller;

import com.cisco.onep.element.NetworkApplication;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ConnectionFactory {

    private final String address;
    private final String password;

    public ConnectionFactory(String address, String password) {
        this.address = address;
        this.password = password;
        NetworkApplication networkApplication = NetworkApplication.getInstance();
    }

}
