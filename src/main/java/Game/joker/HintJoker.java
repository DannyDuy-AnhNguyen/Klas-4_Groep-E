package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

//Beschikbaar in alle kamers
public class HintJoker implements ApplicableJoker{
    private boolean used = false;

    @Override
    public void useIn(Kamer kamer, Speler speler){ //speler variabele wordt niet gebruikt, maar schendt ook niet de LSP
        if (isUsed()) {
            System.out.println("❌Deze Hintjoker is al gebruikt🙂‍↔️.");
            return;
        }

        if (!canBeUsedIn(kamer)) {
            System.out.println("❌Deze Hintjoker werkt hier niet🙂‍↔️.");
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
    public boolean canBeUsedIn(Kamer kamer){
        return true;
    }

}
