package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface Joker {
    boolean isUsed();
    boolean canBeUsedIn(Kamer kamer);
    void useIn(Kamer kamer, Speler speler);
}
