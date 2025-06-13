package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface Joker {
    void useIn(Kamer kamer, Speler speler);
    boolean isUsed();
    String getNaam();
}
