package sk.fiit.aks.fail2ban.controller;

import sk.fiit.aks.fail2ban.enitiy.Router;
import java.util.ArrayList;
import java.util.List;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ElementRegistry {

    private static ElementRegistry instance;
    private final List<Router> routers = new ArrayList<>();
    private final ConnectionFactory connectionFactory;

    private ElementRegistry() {
        this.connectionFactory = new ConnectionFactory();
    }

    public static ElementRegistry getInstance() {
        if (instance == null) {
            instance = new ElementRegistry();
        }
        return instance;
    }

    public void registerRouter(String ipaddress, String username, String password, String name) throws Fail2banConnectionException {
        Router router = new Router(this.connectionFactory.createConnection(username, password, ipaddress), name);
        this.routers.add(router);
    }

    public Router getRouter(String id) throws Fail2banConnectionException {
        for (Router router : routers) {
            if (router.getId().equals(id)) {
                return router;
            }
        }
        throw new Fail2banConnectionException("Router with id " + id + " not found");
    }

    public List<Router> getAllRouters() {
        return this.routers;
    }
}
