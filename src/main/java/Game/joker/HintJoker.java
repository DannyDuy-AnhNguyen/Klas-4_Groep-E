package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

//Beschikbaar in alle kamers
public class HintJoker extends AbstractJoker {
    public HintJoker(String naam){
        super(naam);
    }

    @Override
    public void useIn(Kamer kamer, Speler speler){
        if (used) {
            System.out.println("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }
        kamer.toonHint();
        used = true;
    }

}
