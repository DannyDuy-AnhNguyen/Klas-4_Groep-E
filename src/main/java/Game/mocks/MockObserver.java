package Game.mocks;

import Game.core.Observer;    // Je interface
import Game.core.Speler;      // Omdat update(Speler) vereist is

public class MockObserver implements Observer {
    public boolean isBijgewerkt = false;

    @Override
    public void update(Speler speler) {
        isBijgewerkt = true;
    }
}
