package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

import java.util.Set;

//Beschikbaar in 2 specifieke kamers. Daily Scrum en Review
public class KeyJoker extends AbstractJoker {
    //Deze variabele zorgt ervoor dat alleen de toegevoegde kamers de keys werken op basis van de 'KamerFactory'.
    public KeyJoker(String naam){
        super(naam);
    }

    @Override
    public void useIn(Kamer kamer, Speler speler) {
        if (used) {
            System.out.println("❌ KeyJoker is al gebruikt.");
            return;
        }

        //
        if (!kamer.accepteertKeyJoker()) {
            System.out.println("🚫 Deze kamer accepteert geen KeyJoker.");
            return;
        }

        //Omdat je alleen binnen twee kamers de key kunt gebruiken,
        //is het ook handig als de maximale sleutels 2 is.
        if (speler.getSleutels() >= 2){
            System.out.println("❌ Je hebt al het maximale aantal keys gebruikt!");
            return;
        }

        kamer.geefExtraSleutel(speler);
        System.out.println("🔐 KeyJoker gebruikt in kamer: " + kamer.getNaam());
        System.out.println("🗝️Aantal sleutels die je nog kan gebruiken: " + speler.getKeysLeft());
        used = true;
    }

    //Deze methode wordt gebruikt in beide joker sub klasses🙂
    @Override
    public boolean isUsed() {
        return used;
    }

    //Deze methode wordt gebruikt in beide joker sub klasses🙂

    @Override
    public String getNaam() {
        return naam;
    }

}


