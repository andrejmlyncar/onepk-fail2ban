package sk.fiit.aks.fail2ban.servlet;

// Import required java libraries
import java.io.*;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AclServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(200);

        JsonObjectBuilder objectBuilder;
        objectBuilder = Json.createObjectBuilder()
                .add("data", "Obtaining access lists");

        response.getWriter().write(objectBuilder.build().toString());
    }

    @Override
    public void destroy() {
    }
}
