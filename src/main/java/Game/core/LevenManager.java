package Game.core;

public class LevenManager {
    private final Speler speler;

    public LevenManager(Speler speler) {
        this.speler = speler;
    }

    public void verliesLeven() {
        speler.setLevens(speler.getLevens() - 1);
        System.out.println("💔 Je hebt een leven verloren! Resterende levens: " + speler.getLevens());
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