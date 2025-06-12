package Game.core;

import Game.joker.Joker;

import java.util.List;

public class JokerManager {
    private final Speler speler;
    private static final int MAX_JOKERS = 2;
    public JokerManager(Speler speler) {
        this.speler = speler;
    }

    public boolean voegJokerToe(Joker joker) {
        List<Joker> jokers = speler.getJokers();

        if (jokers.size() >= MAX_JOKERS) {
            TextPrinter.print("❌ Je hebt al het maximum aantal jokers (" + MAX_JOKERS + ") bereikt.");
            System.out.println();
            return false;
        }
        jokers.add(joker);
        return true;
    }


}