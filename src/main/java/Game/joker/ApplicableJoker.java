package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

public interface ApplicableJoker extends Joker{
    void useIn(Kamer kamer, Speler speler);
}
