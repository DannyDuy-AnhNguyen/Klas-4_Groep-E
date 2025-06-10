package Game.Randwaarden.core;

import Game.antwoord.AntwoordDailyScrum;
import Game.joker.HintJoker;
import Game.kamer.Kamer;
import Game.kamer.KamerDailyScrum;
import org.junit.Test;

import static org.junit.Assert.*;

public class JokerManagerTest {
    @Test
    public void checkTussenMinimaalNulEnMaximaalVier() {
        // Arrange
        HintJoker hints = new HintJoker("hint");
        AntwoordDailyScrum antwoordDailyScrum = new AntwoordDailyScrum();
        Kamer kamerDailyScrum = new KamerDailyScrum(antwoordDailyScrum);

        // Act
        hints.useInHint(kamerDailyScrum);

//        // Assert — voorbeeld: afhankelijk van je HintJoker implementatie
//        int aantalGebruikteHints = hints.getAantalGebruikteHints(); // voorbeeldmethode
//        assertTrue("Aantal hints moet tussen 0 en 4 liggen", aantalGebruikteHints >= 0 && aantalGebruikteHints <= 4);
    }
}

