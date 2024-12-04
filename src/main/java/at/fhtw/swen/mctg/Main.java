package at.fhtw.swen.mctg;

import at.fhtw.swen.mctg.services.cards.StackService;
import at.fhtw.swen.mctg.services.deck.DeckService;
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import at.fhtw.swen.mctg.services.packages.PackageService;
import at.fhtw.swen.mctg.services.registration.RegistrationService;
import at.fhtw.swen.mctg.httpserver.server.Server;
import at.fhtw.swen.mctg.httpserver.utils.Router;
import at.fhtw.swen.mctg.services.login.LoginService;
import  at.fhtw.swen.mctg.services.cardacquisition.CardAcquisitionService;

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
        AuthenticationService authenticationService = new AuthenticationService();
        Router router = new Router();
        router.addService("/sessions", new LoginService(authenticationService));
        router.addService("/users", new RegistrationService(authenticationService));
        router.addService("/packages", new PackageService());
        router.addService("/transactions/packages", new CardAcquisitionService(authenticationService));
        router.addService("/cards", new StackService(authenticationService));
        router.addService("/deck", new DeckService(authenticationService));
        return router;
    }
}
