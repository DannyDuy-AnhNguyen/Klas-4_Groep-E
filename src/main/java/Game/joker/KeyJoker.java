package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

import java.util.Set;

//Beschikbaar in 2 specifieke kamers. Daily Scrum en Review
public class KeyJoker extends AbstractJoker implements KeyJokerInterface {
    //Deze variabele zorgt ervoor dat alleen de toegevoegde kamers de keys werken op basis van de 'KamerFactory'.
    private static final Set<String> toegestaandeKamers = Set.of("Daily Scrum", "Sprint Review");

    public KeyJoker(String naam){
        super(naam);
    }

    @Override
    public void useInKey(Kamer kamer, Speler speler) {
        if (used) {
            System.out.println("❌ Deze KeyJoker is al gebruikt.");
            return;
        }

        // Dit mag: intern gedrag dat beperkt werkt in bepaalde kamers
        if (kamer.getNaam().equalsIgnoreCase("Daily Scrum") || kamer.getNaam().equalsIgnoreCase("Sprint Review")) {
            kamer.geefExtraSleutel(speler);
            System.out.println("🔐 KeyJoker gebruikt in kamer: " + kamer.getNaam());
        } else {
            System.out.println("ℹ️ De KeyJoker heeft hier geen effect.");
        }

        used = true;
    }

    @Override
    public boolean canBeUsedIn(Kamer kamer) {
        return toegestaandeKamers.contains(kamer.getNaam());
    }

}
