package at.fhtw.swen.mctg.services.cardacquisition;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.CardDao;
import at.fhtw.swen.mctg.persistence.dao.PackageDao;
import at.fhtw.swen.mctg.persistence.dao.StackRepository;
import at.fhtw.swen.mctg.persistence.dao.UserRepository;

import static at.fhtw.swen.mctg.model.Package.PACKAGE_PRICE;

public class CardAcquisitionController extends Controller {
    public Response acquisiteCards(String token) {
        UnitOfWork unitOfWork = new UnitOfWork();
        try(unitOfWork) {
            UserRepository userRepository = new UserRepository(unitOfWork);
            User user = userRepository.findUserByToken(token);
            int availableCoins = user.getCoins();
            if (availableCoins < PACKAGE_PRICE) {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        "{ \"message\": \"Not enough coins available\"}"
                );
            }

            int userStackId = new StackRepository(unitOfWork).findStackByUsername(user.getLogin());
            int packageId = new PackageDao(unitOfWork).getRandomPackageId();

            CardDao cardDao = new CardDao(unitOfWork);
            cardDao.assignCardsToStack(packageId, userStackId);
            cardDao.clearPackageId(packageId);
            user.spendFiveCoins();
            userRepository.updateCoins(user);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    "{ \"message\": \"Card acquisition successful\"}"
            );
        } catch (DataAccessException e) {
                return new Response(HttpStatus.UNAUTHORIZED,
                        "{ \"message\": " + e.getMessage() + "}");
        } catch (Exception e) {
            unitOfWork.rollbackTransaction();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "{ \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}
