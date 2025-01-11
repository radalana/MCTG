package at.fhtw.swen.mctg.services.cardacquisition;

import at.fhtw.swen.mctg.core.controller.Controller;
import at.fhtw.swen.mctg.exceptions.UserNotFoundException;
import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;
import at.fhtw.swen.mctg.model.User;
import at.fhtw.swen.mctg.persistence.DataAccessException;
import at.fhtw.swen.mctg.persistence.UnitOfWork;
import at.fhtw.swen.mctg.persistence.dao.cards.CardDao;
import at.fhtw.swen.mctg.persistence.dao.cards.PackageDao;
import at.fhtw.swen.mctg.persistence.dao.user.UserRepository;

import static at.fhtw.swen.mctg.httpserver.http.MessageConstants.*;
import static at.fhtw.swen.mctg.model.Package.PACKAGE_PRICE;
//TODO только 1 пакет юзер купил его, и потом может купить его еще раз ФИКСИТ!
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
                        NOT_ENOUGH_COINS
                );
            }
            
            int packageId = new PackageDao(unitOfWork).getFirstCreatedPackageId();

            CardDao cardDao = new CardDao(unitOfWork);
            cardDao.assignCardsToUser(user.getId(), packageId);
            new PackageDao(unitOfWork).delete(packageId);
            user.spendFiveCoins();
            userRepository.updateCoins(user);
            unitOfWork.commitTransaction();
            return new Response(
                    HttpStatus.OK,
                    CARDS_PURCHASED_SUCCESSFULLY
            );
        }catch (UserNotFoundException e) {
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    USER_NOT_FOUND
            );
        }catch (DataAccessException e) {
            //TODO другой  response!!!
                return new Response(HttpStatus.BAD_REQUEST,
                        "{ \"message\": " + e.getMessage() + "}");
        } catch (Exception e) {
            unitOfWork.rollbackTransaction();
            //TODO more logic errors login
            System.err.println(e.getMessage());
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                            INTERNAL_SERVER_ERROR
            );
        }
    }
}
