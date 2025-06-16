package Game.Randwaarden.kamer;

import Game.antwoord.*;
import Game.core.Speler;
import Game.joker.HintJoker;
import Game.kamer.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KamerBetreedTest {

    //De setup om de duplicatie code te voorkomen.
    private Kamer maakKamer() {
        return new KamerDailyScrum(new AntwoordDailyScrum());
    }

    private Speler maakSpelerMetHintJokers(int aantal) {
        Speler speler = new Speler();
        for (int i = 0; i < aantal; i++) {
            speler.voegJokerToe(new HintJoker("hint"));
        }
        return speler;
    }

    private KamerBetreed maakKamerBetreedMetInput(String input) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        return new KamerBetreed(scanner);
    }


    //Bij deze test is het gebruiken van de hintJoker maximaal 4.
    @Test
    public void checkSpelerKanMaximaalVierHintJokersGebruiken() {
        Kamer kamer = maakKamer();
        Speler speler = maakSpelerMetHintJokers(4);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("hint\nhint\nhint\nhint\n");

        kamerBetreed.betreed(kamer, speler);

        assertEquals("Speler mag maximaal 4 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Na 4 hints zijn er geen hints meer beschikbaar", 0, speler.getHintsLeft());
    }

    //IN deze test is de minimale hint minimaal 0. Ook mag de minimale of maximale waarde omloog of omhoog gaan zodra de gebruiker 'annuleer' als input geeft
    // Als de gebruiker al binnen een kamer 'joker' als input gegeven heeft.
    @Test
    public void checkMinimaleWaardeOfHetNietOmhoogOfOmlaagGaat() {
        Kamer kamer = maakKamer();
        Speler speler = maakSpelerMetHintJokers(1); // Één joker is genoeg om te testen
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("annuleer\n");

        kamerBetreed.verwerkJoker(kamer, speler);

        assertEquals("Geen hints gebruikt bij geen geldige invoer", 0, speler.getHintCounter());
        assertEquals("Speler heeft nog 4 hints beschikbaar" + speler.getHintsLeft(), 4, speler.getHintsLeft());
    }


    //IN deze test probeert de speler meer dan 4 hints te gebruiken.
    //Als de test werkt, zou de speler een foutmelding ontvangen na de maximale aantal hints.
    @Test
    public void checkSpelerMagGeenVijfHintJokersGebruiken(){
        Kamer kamer = maakKamer();
        Speler speler = maakSpelerMetHintJokers(5);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("hint\nhint\nhint\nhint\nhint\n");

        for (int i = 0; i < 5; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        kamerBetreed.betreed(kamer, speler);
        assertEquals("Speler mag mag niet meer dan 5 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Als de hintCounter tot 0 komt, dan kan de speler geen hints meer gebruiken oftewel de waarde is 0", 0, speler.getHintsLeft());
    }

}