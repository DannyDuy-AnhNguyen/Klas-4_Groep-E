package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

import java.util.Set;

//Beschikbaar in 2 specifieke kamers. Daily Scrum en Review
public class KeyJoker implements ApplicableJoker, ToegestaandeKamers {
    private boolean used = false;
    private static final Set<String> toegestaandeKamers = Set.of("Daily Scrum", "Review");

    @Override
    public void useIn(Kamer kamer, Speler speler){
        if (isUsed()) {
            System.out.println("❌Deze Keyjoker is al gebruikt🔐.");
            return;
        }

        if (!canBeUsedIn(kamer)) {
            System.out.println("❌Deze Keyjoker werkt hier niet🔐.");
            return;
        }

        kamer.geefExtraSleutel(speler); // ✅ Geeft sleutel via kamer aan speler
        System.out.println("🔐 KeyJoker gebruikt in kamer: " + kamer.getNaam());
        used = true;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public boolean canBeUsedIn(Kamer kamer){
        System.out.println("De keyjoker kan alleen in deze 2 kamers gebruikt worden: "+ getToegestaandeKamers());
        return toegestaandeKamers.contains(kamer.getNaam());
    }

    @Override
    public Set<String> getToegestaandeKamers(){
        return toegestaandeKamers;
    }

}
