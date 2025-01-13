package at.fhtw.swen.mctg;

import at.fhtw.swen.mctg.services.battle.BattleService;
import at.fhtw.swen.mctg.services.cards.StackService;
import at.fhtw.swen.mctg.services.deck.DeckService;
import at.fhtw.swen.mctg.services.login.AuthenticationService;
import at.fhtw.swen.mctg.services.packages.PackageService;
import at.fhtw.swen.mctg.services.score.stats.UserStatsService;
import at.fhtw.swen.mctg.services.user.UsersService;
import at.fhtw.swen.mctg.httpserver.server.Server;
import at.fhtw.swen.mctg.httpserver.utils.Router;
import at.fhtw.swen.mctg.services.login.LoginService;
import  at.fhtw.swen.mctg.services.cardacquisition.CardAcquisitionService;
import at.fhtw.swen.mctg.services.trade.TradingService;

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
        router.addService("/users", new UsersService(authenticationService));
        router.addService("/packages", new PackageService());
        router.addService("/transactions", new CardAcquisitionService());
        router.addService("/cards", new StackService());
        router.addService("/deck", new DeckService(authenticationService));
        router.addService("/battles", new BattleService());
        router.addService("/tradings", new TradingService());
        router.addService("/stats", new UserStatsService());
        return router;
    }
}
