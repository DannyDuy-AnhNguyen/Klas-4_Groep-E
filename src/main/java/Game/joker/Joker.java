package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface Joker {
    boolean isUsed();

    //UseIn wordt later verwijderd van deze interface
    void useIn(Kamer kamer, Speler speler);
    String getNaam();
}
