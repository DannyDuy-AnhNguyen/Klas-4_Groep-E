package Game.mocks;

import Game.core.Observer;    // Je interface
import Game.core.Speler;      //
public class MockObserver implements Observer {
    public boolean isBijgewerkt = false;

    @Override
    public void update(Speler speler) {
        isBijgewerkt = true;
    }
}
