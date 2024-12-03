package at.fhtw.swen.mctg.model;

import at.fhtw.swen.mctg.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Round {
    private User winner; //или карта
    private User looser; //или карта
    private Card playedCard;
}


