package at.fhtw.swen.mctg;

import at.fhtw.swen.mctg.httpserver.server.Service;
import at.fhtw.swen.mctg.services.packages.PackageService;
import at.fhtw.swen.mctg.services.registration.RegistrationService;
import at.fhtw.swen.mctg.httpserver.server.Server;
import at.fhtw.swen.mctg.httpserver.utils.Router;
import at.fhtw.swen.mctg.services.login.LoginService;

import java.io.IOException;



public class Main{
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        router.addService("/sessions", new LoginService());
        router.addService("/users", new RegistrationService());
        router.addService("/packages", new PackageService());

        return router;
    }
}
