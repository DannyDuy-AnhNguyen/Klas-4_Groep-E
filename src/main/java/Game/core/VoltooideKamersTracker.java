package Game.core;

import java.util.List;

public class VoltooideKamersTracker {
    private final Speler speler;

    public VoltooideKamersTracker(Speler speler) {
        this.speler = speler;
    }

    public void voegKamerToe(int kamerIndex) {
        List<Integer> lijst = speler.getVoltooideKamers();
        if (!lijst.contains(kamerIndex)) {
            lijst.add(kamerIndex);
            speler.notifyObservers();
        }
    }
}
