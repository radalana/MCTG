package at.fhtw.swen.mctg.model.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardData {
    private final String Id;
    private final String Name;
    private final double Damage;

    @JsonCreator
    public CardData(@JsonProperty("Id") String id,
                    @JsonProperty("Name") String name,
                    @JsonProperty("Damage") double damage) {

        Id = id;
        Name = name;
        Damage = damage;
    }
    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getDamage() {
        return Damage;
    }
}
