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
    public List<Map<String, Object>> getCardsAsMap(int stackId) {
        List<Card> cards = cardDao.getCardsByStackId(stackId);
        return cards.stream().map(card -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", card.getId());
            map.put("name", card.getName());
            map.put("damage", card.getDamage());
            return map;
        }).toList();
    }
}
