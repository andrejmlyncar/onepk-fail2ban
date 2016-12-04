package sk.fiit.aks.fail2ban.servlet;

// Import required java libraries
import com.cisco.onep.policy.L3Ace;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.entity.BannedRecord;
import sk.fiit.aks.fail2ban.entity.Router;
import sk.fiit.aks.fail2ban.exception.AccessListManagerException;

public class AclServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            for (Router router : ElementRegistry.getInstance().getAllRouters()) {
                JsonObjectBuilder routerBuilder = Json.createObjectBuilder();
                JsonArrayBuilder aceArrayBuilder = Json.createArrayBuilder();

                for (BannedRecord bannedRecord : router.getAccessListManager().getBannedRecords()) {
                    L3Ace ace = bannedRecord.getAce();
                    JsonObjectBuilder aceBuilder = Json.createObjectBuilder();                    
                    String[] parsedAce = ace.toString().split("\n");                                        
                    aceBuilder.add("sequence", parsedAce[2].substring(parsedAce[2].lastIndexOf(":") + 1));
                    aceBuilder.add("permit",  parsedAce[3].substring(parsedAce[3].lastIndexOf(":") + 1));
                    aceBuilder.add("source",  parsedAce[5].substring(parsedAce[5].lastIndexOf(":") + 1));
                    aceBuilder.add("destination",  parsedAce[6].substring(parsedAce[6].lastIndexOf(":") + 1));
                    aceBuilder.add("expireTime", dateFormat.format(bannedRecord.getExpireTimestamp()));
                    aceArrayBuilder.add(aceBuilder.build());
                }
                routerBuilder.add("router_id", router.getId());
                routerBuilder.add("aces", aceArrayBuilder.build());
                builder.add(routerBuilder.build());
            }
            response.setContentType("application/json");
            response.setStatus(200);
            response.getWriter().write(builder.build().toString());
        } catch (AccessListManagerException ex) {
            response.setStatus(500);
            response.getWriter().write(ex.toString());
        }
    }

    @Override
    public void destroy() {
    }
}
