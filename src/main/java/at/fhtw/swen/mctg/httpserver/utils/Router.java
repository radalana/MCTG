package at.fhtw.swen.mctg.httpserver.utils;

import at.fhtw.swen.mctg.core.services.login.LoginService;
import at.fhtw.swen.mctg.httpserver.server.Service;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, LoginService> serviceRegistry = new HashMap<>();

    //TODO вместо loginService  Service - общий класс, но его надо сделать
    public void addService(String route, LoginService loginService)
    {
        this.serviceRegistry.put(route, loginService);
    }

    //реши какой сервис запустить
    public Service resolve(String route) {
        return this.serviceRegistry.get(route);
    }
}
