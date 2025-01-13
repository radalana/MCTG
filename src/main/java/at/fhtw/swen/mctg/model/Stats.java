package at.fhtw.swen.mctg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Stats {
    private static final int LOSS = -5;
    private static final int WIN = 3;
    @JsonIgnore
    private final int user_id;
    private int wins;
    private int losses;
    private int draws;
    private int total_battles;
    private int elo;

    public void recordWin(){
        wins++;
        total_battles++;
        elo+=WIN;
    }
    public void recordLoss(){
        losses++;
        total_battles++;
        elo+=LOSS;
    }
    public void recordDraw(){
        draws++;
        total_battles++;
    }
}


