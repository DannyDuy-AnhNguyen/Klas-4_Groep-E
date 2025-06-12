package Game.joker;

import Game.core.TextPrinter;
import Game.kamer.Kamer;

//Beschikbaar in alle kamers
public class HintJoker extends AbstractJoker implements HintJokerInterface{
    public HintJoker(String naam){
        super(naam);
    }

    @Override
    public void useInHint(Kamer kamer){
        if (used) {
            TextPrinter.print("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }
        kamer.toonHint();
        used = true;
    }

}
