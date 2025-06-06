package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

//Beschikbaar in alle kamers
public class HintJoker extends AbstractJoker implements HintJokerInterface{

    public HintJoker(String naam){
        super(naam);
    }

    @Override
    public void useInHint(Kamer kamer){
        System.out.println("Zit in Hint Methode🙂:");
        if (used) {
            System.out.println("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }
        kamer.toonHint();
        used = true;
    }
}
