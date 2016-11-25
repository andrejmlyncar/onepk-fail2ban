package sk.fiit.aks.fail2ban.servlet;

// Import required java libraries
import com.cisco.onep.policy.L3Ace;
import com.cisco.onep.policy.L3Acl;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;

public class AclServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("router_id");
        try {
            L3Acl accessList = ElementRegistry.getInstance().getRouter(id).getAccessListManager().getAccessList();
            response.setContentType("application/json");
            response.setStatus(200);
            for(L3Ace ace : accessList.getAceList()) {
                
            }
            
        } catch (Fail2banConnectionException | AccessListManagerException ex) {
            response.setStatus(500);
            response.getWriter().write(ex.toString());
        } 
        JsonObjectBuilder objectBuilder;
        objectBuilder = Json.createObjectBuilder()
                .add("data", "Obtaining access lists");

        response.getWriter().write(objectBuilder.build().toString());
    }

    @Override
    public void destroy() {
    }
}
