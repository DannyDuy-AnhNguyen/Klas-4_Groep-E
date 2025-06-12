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

    //Bij deze test is het gebruiken van de hintJoker maximaal 4.
    @Test
    public void checkSpelerKanMaximaalVierHintJokersGebruiken() {
        Antwoord antwoord = new AntwoordDailyScrum();
        Kamer kamer = new KamerDailyScrum(antwoord);

        String testInput = "hint\nhint\nhint\nhint\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(testInput.getBytes()));
        KamerBetreed kamerBetreed = new KamerBetreed(scanner);

        Speler speler = new Speler();
        for (int i = 0; i < 4; i++) {
            speler.voegJokerToe(new HintJoker("hint"));
        }

        for (int i = 0; i < 4; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        kamerBetreed.betreed(kamer, speler);

        assertEquals("Speler mag maximaal 4 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Na 4 hints zijn er geen hints meer beschikbaar", 0, speler.getHintsLeft());
    }

    //IN deze test is de minimale hint minimaal 0. Ook mag de minimale of maximale waarde omloog of omhoog gaan zodra de gebruiker 'annuleer' als input geeft
    // Als de gebruiker al binnen een kamer 'joker' als input gegeven heeft.
    @Test
    public void checkMinimaleWaardeOfHetNietOmhoogOfOmlaagGaat() {
        Antwoord antwoord = new AntwoordDailyScrum();
        Kamer kamer = new KamerDailyScrum(antwoord);

        String testInput = "annuleer\n"; // of "\n" als je wilt dat gebruiker niets typt
        Scanner scanner = new Scanner(new ByteArrayInputStream(testInput.getBytes()));
        KamerBetreed kamerBetreed = new KamerBetreed(scanner);

        Speler speler = new Speler();
        speler.voegJokerToe(new HintJoker("hint"));

        kamerBetreed.verwerkJoker(kamer, speler);

        assertEquals("Geen hints gebruikt bij geen geldige invoer", 0, speler.getHintCounter());
        assertEquals("Speler heeft nog 4 hints beschikbaar" + speler.getHintsLeft(), 4, speler.getHintsLeft());
    }


    //IN deze test probeert de speler meer dan 4 hints te gebruiken.
    //Als de test werkt, zou de speler een foutmelding ontvangen na de maximale aantal hints.
    @Test
    public void checkSpelerMagGeenVijfHintJokersGebruiken(){
        Antwoord antwoord = new AntwoordDailyScrum();
        Kamer kamer = new KamerDailyScrum(antwoord);

        String testInput = "hint\nhint\nhint\nhint\nhint\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(testInput.getBytes()));
        KamerBetreed kamerBetreed = new KamerBetreed(scanner);

        Speler speler = new Speler();
        for (int i = 0; i < 5; i++) {
            speler.voegJokerToe(new HintJoker("hint"));
        }

        for (int i = 0; i < 5; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        kamerBetreed.betreed(kamer, speler);

        assertEquals("Speler mag maximaal 4 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Na 4 hints zijn er geen hints meer beschikbaar", 0, speler.getHintsLeft());
    }

}