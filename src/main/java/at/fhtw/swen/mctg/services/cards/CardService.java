package at.fhtw.swen.mctg.services.cards;

import at.fhtw.swen.mctg.model.Card;
import at.fhtw.swen.mctg.persistence.dao.CardDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardService {
    private final CardDao cardDao;
    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public List<Map<String, Object>> getAllCardsAsMap(int userId) {
        List<Card> cards = cardDao.getCardsByUserId(userId);
        return convertCardsToMap(cards);
    }

    public List<Map<String, Object>> getCardsFromDeckAsMap(int userId) {
        List<Card> cards = cardDao.getCardsInDeckByUserId(userId);
        return convertCardsToMap(cards);
    }

    private List<Map<String, Object>> convertCardsToMap(List<Card> cards) {
        return cards.stream().map(card -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", card.getId());
            map.put("name", card.getName());
            map.put("damage", card.getDamage());
            return map;
        }).toList();
    }
}
