package at.fhtw.swen.mctg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Stats {
    private final int user_id;
    private int wins;
    private int losses;
    private int draws;
    private int total_battles;
}
