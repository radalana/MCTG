package at.fhtw.swen.mctg.httpserver.utils;

import at.fhtw.swen.mctg.core.services.login.LoginService;
import at.fhtw.swen.mctg.httpserver.server.Service;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Service> serviceRegistry = new HashMap<>();

    public void addService(String route, Service service)
    {
        this.serviceRegistry.put(route, service);
    }

    //реши какой сервис запустить
    public Service resolve(String route) {
        return this.serviceRegistry.get(route);
    }
}
