/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fiit.aks.fail2ban.servlet.util;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import sk.fiit.aks.fail2ban.exception.Fail2BanServletException;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class ServletDataReader {

    public static JsonObject getJsonData(HttpServletRequest request) throws Fail2BanServletException {
        try {
            JsonReader jsonReader = Json.createReader(request.getReader());
            JsonObject jsonObject = jsonReader.readObject();
            return jsonObject;
        } catch (IOException ex) {
            throw new Fail2BanServletException("Error parsing request data: Invalid Json.", ex);
        }
    }
}
