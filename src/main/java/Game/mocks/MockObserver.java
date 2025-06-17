package Game.mocks;

import Game.core.Observer;    // Je interface
import Game.core.Speler;      //

public class MockObserver implements Observer {
    public int aantalUpdates = 0;

    @Override
    public void update(Speler speler) {
        aantalUpdates++;
    }
}

