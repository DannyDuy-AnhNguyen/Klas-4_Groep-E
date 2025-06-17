package Game.Randwaarden.kamer;

import Game.antwoord.*;
import Game.core.Speler;
import Game.joker.HintJoker;
import Game.kamer.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class KamerBetreedTest {

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

    //ByteArrayInputStream doet alsof die bytes echte invoer zijn.
    //testInput.getBytes() zet een string om in bytes.
    private KamerBetreed maakKamerBetreedMetInput(String input) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        return new KamerBetreed(scanner);
    }

    //Maximale aantal
    @Test
    public void checkSpelerKanMaximaalVierHintJokersGebruiken() {
        Kamer kamer = maakKamer();
        kamer.getDeur().setOpen(true);
        Speler speler = maakSpelerMetHintJokers(4);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("hint\nhint\nhint\nhint\n");

        for (int i = 0; i < 4; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        assertEquals("Speler mag maximaal 4 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Na 4 hints zijn er geen hints meer beschikbaar", 0, speler.getHintsLeft());
    }

    //Minimale waarde
    @Test
    public void checkMinimaleWaardeOfHetNietOmhoogOfOmlaagGaat() {
        Kamer kamer = maakKamer();
        Speler speler = maakSpelerMetHintJokers(1);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("annuleer\n");

        kamerBetreed.betreed(kamer, speler);

        assertEquals("Geen hints gebruikt bij geen geldige invoer", 0, speler.getHintCounter());
        assertEquals("Speler heeft nog 4 hints beschikbaar, maar had: " + speler.getHintsLeft(), 4, speler.getHintsLeft());
    }

    //Maximale aantal
    @Test
    public void checkSpelerBinnenBereik() {
        Kamer kamer = maakKamer();
        kamer.getDeur().setOpen(true);
        Speler speler = maakSpelerMetHintJokers(2);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("hint\nhint\nhint\nhint\n");

        for (int i = 0; i < 2; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        assertEquals("Speler mag maximaal 4 hints gebruiken", 2, speler.getHintCounter());
        assertEquals("Na 4 hints zijn er geen hints meer beschikbaar", 2, speler.getHintsLeft());
    }

    //Boven Maximaal
    @Test
    public void checkSpelerMagGeenVijfHintJokersGebruiken(){
        Kamer kamer = maakKamer();
        kamer.getDeur().setOpen(true);
        Speler speler = maakSpelerMetHintJokers(5);
        KamerBetreed kamerBetreed = maakKamerBetreedMetInput("hint\nhint\nhint\nhint\nhint\n");

        for (int i = 0; i < 5; i++) {
            kamerBetreed.verwerkJoker(kamer, speler);
        }

        assertEquals("Speler mag niet meer dan 4 hints gebruiken", 4, speler.getHintCounter());
        assertEquals("Als de hintCounter tot 0 komt, dan kan de speler geen hints meer gebruiken oftewel de waarde is 0", 0, speler.getHintsLeft());
    }
}
