package at.fhtw.swen.mctg.model.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CardData {
    private final String Id;
    private final String Name;
    private final double Damage;
    private String Type;
    private String SubType;
    private String Element;


    //Constructor für das Erstellen von CardData-Objekten aus HTTP-Anfragen
    @JsonCreator
    public CardData(@JsonProperty("Id") String id,
                    @JsonProperty("Name") String name,
                    @JsonProperty("Damage") double damage) {

        Id = id;
        Name = name;
        Damage = damage;
    }

    //Constructor für das Laden von CardData aus der Datenbank
    //Dient als Zwischenschritt zur Erstellung von echten Card objecten
    public CardData(String id, String name, String type, String subtype, Double damage, String element) {
        Id = id;
        Name = name;
        Damage = damage;
        Type = type;
        SubType = subtype;
        Element = element;
    }
}
