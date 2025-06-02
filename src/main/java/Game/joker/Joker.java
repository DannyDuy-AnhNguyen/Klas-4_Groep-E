package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface Joker {
    boolean isUsed();
    void useIn(Kamer kamer, Speler speler);
    String getNaam();

}
