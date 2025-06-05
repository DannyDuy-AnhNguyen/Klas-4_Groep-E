package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

//Beschikbaar in alle kamers
public class HintJoker implements Joker, HintJokerInterface{
    private boolean used = false;

    @Override
    public void useIn(Kamer kamer, Speler speler){ //speler variabele wordt niet gebruikt, maar schendt ook niet de LSP
        if (used) {
            System.out.println("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }

        kamer.toonHint();
        used = true;
    }

    @Override
    public void useInHint(Kamer kamer){
        if (used) {
            System.out.println("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }

        kamer.toonHint();
        used = true;
    }


    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public String getNaam() {
        return "hint";
    }
}
