package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

import java.util.Set;

//Beschikbaar in 2 specifieke kamers. Daily Scrum en Review
public class DailyScrumKeyJoker extends AbstractJoker {
    //Deze variabele zorgt ervoor dat alleen de toegevoegde kamers de keys werken op basis van de 'KamerFactory'.
    public DailyScrumKeyJoker(String naam){
        super(naam);
    }

    @Override
    public void useIn(Kamer kamer, Speler speler) {
        if (used) {
            System.out.println("❌ KeyJoker is al gebruikt.");
            return;
        }
        kamer.geefExtraSleutel(speler);
        System.out.println("🔐 KeyJoker gebruikt in kamer: " + kamer.getNaam());
        used = true;
    }
}


