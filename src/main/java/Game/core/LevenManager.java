package Game.core;

import static Game.core.ConsoleKleuren.*;

public class LevenManager {
    private final Speler speler;

    public LevenManager(Speler speler) {
        this.speler = speler;
    }

    public void verliesLeven() {
        speler.setLevens(speler.getLevens() - 1);
        System.out.println( RED + "Je hebt een leven verloren! " + RESET + "Resterende levens: " + speler.getLevens());
        System.out.println();
        speler.notifyObservers();

        if (speler.getLevens() <= 0) {
            speler.gameOver();
        }
    }

    public void resetLevens() {
        speler.setLevens(3);
        speler.notifyObservers();
    }
}