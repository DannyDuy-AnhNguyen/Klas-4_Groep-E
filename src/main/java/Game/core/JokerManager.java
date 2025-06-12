package Game.core;

import Game.joker.Joker;

import java.util.List;

public class JokerManager {
    private final Speler speler;
    public JokerManager(Speler speler) {
        this.speler = speler;
    }

    public boolean voegJokerToe(Joker joker) {
        List<Joker> jokers = speler.getJokers();
        jokers.add(joker);
        return true;
    }


}