package Game.core;

import Game.joker.Joker;

import java.util.List;

public class JokerManager {
    private final Speler speler;
    private static final int MAX_JOKERS = 2;
    private int hintCounter;

    public JokerManager(Speler speler) {
        this.speler = speler;
        this.hintCounter = 0;
    }

    public boolean voegJokerToe(Joker joker) {
        List<Joker> jokers = speler.getJokers();

        if (jokers.size() >= MAX_JOKERS) {
            System.out.println("❌ Je hebt al het maximum aantal jokers (" + MAX_JOKERS + ") bereikt.");
            return false;
        }

        System.out.println("Totale jokers: "+ speler.getJokers().size());
        System.out.println("Totale gebruikte hints🙂: "+ hintCounter);
        jokers.add(joker);
        return true;
    }

    public boolean maximalHints(){
        return hintCounter <= 2;
    }

}