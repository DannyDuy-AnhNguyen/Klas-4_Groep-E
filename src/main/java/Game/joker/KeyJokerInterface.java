package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface KeyJokerInterface {
    void useInKey(Kamer kamer, Speler speler);
    boolean canBeUsedIn(Kamer kamer);
}
