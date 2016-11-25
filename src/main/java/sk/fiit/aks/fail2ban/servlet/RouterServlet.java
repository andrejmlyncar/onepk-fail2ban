/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fiit.aks.fail2ban.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sk.fiit.aks.fail2ban.controller.ElementRegistry;
import sk.fiit.aks.fail2ban.enitiy.Router;
import sk.fiit.aks.fail2ban.exception.Fail2BanServletException;
import sk.fiit.aks.fail2ban.exception.Fail2banConnectionException;
import sk.fiit.aks.fail2ban.servlet.util.ServletDataReader;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class RouterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Router router : ElementRegistry.getInstance().getAllRouters()) {
            arrayBuilder.add(Json.createObjectBuilder().add("router_id", router.getId()).add("router_name", router.getName()).add("ip_address", router.getAddress()).build());
        }

        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().write(arrayBuilder.build().toString());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JsonObject object = ServletDataReader.getJsonData(request);
            ElementRegistry.getInstance().registerRouter(object.getString("ip_address"), object.getString("username"), object.getString("password"), object.getString("name"));
            response.setStatus(200);
            response.getWriter().write(Json.createObjectBuilder().add("status", "rotuer registered").build().toString());
        } catch (Fail2BanServletException | Fail2banConnectionException ex) {
            response.sendError(500);
            Logger.getLogger(RouterServlet.class.getName()).log(Level.SEVERE, "Error registering router", ex);
        }
    }

    @Override
    public void destroy() {
    }
}
