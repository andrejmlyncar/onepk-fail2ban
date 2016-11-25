/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fiit.aks.fail2ban.manager.impl;

import com.cisco.onep.core.exception.OnepException;
import com.cisco.onep.element.NetworkElement;
import com.cisco.onep.idl.ExceptionIDL;
import com.cisco.onep.vty.VtyService;
import sk.fiit.aks.fail2ban.exception.LoggingManagerException;
import sk.fiit.aks.fail2ban.manager.LoggingManager;

/**
 *
 * @author Andrej Mlyncar <a.mlyncar@gmail.com>
 */
public class LoggingManagerImpl implements LoggingManager {

    private final NetworkElement element;

    public LoggingManagerImpl(NetworkElement element) {
        this.element = element;
    }

    @Override
    public void getLoggingMessages() throws LoggingManagerException {
        try {
            VtyService vtyService = new VtyService(element);
            vtyService.open();
            String showOnepStatusCmd = "show logging";
            String cliResult = vtyService.write(showOnepStatusCmd);
            System.out.println(cliResult);
            vtyService.close();
        } catch (OnepException | InterruptedException | ExceptionIDL ex) {
            throw new LoggingManagerException("Unable to obtain logging messages", ex);
        }
    }

}
