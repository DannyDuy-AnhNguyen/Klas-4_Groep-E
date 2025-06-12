//package Game.Randwaarden.kamer;
//
//import Game.antwoord.*;
//import Game.core.Speler;
//import Game.joker.HintJoker;
//import Game.kamer.*;
//
//import org.junit.Test;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class KamerBetreedTest {
//
//    // Speler mag niet meer dan 5 hints gebruiken.
//    @Test
//    public void spelerMagGeenVijfdeHintGebruiken() {
//        // Arrange
//        Antwoord antwoord = new AntwoordDailyScrum();
//        Kamer kamer = new KamerDailyScrum(antwoord);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 5; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamer, speler);
//        }
//
//        // Assert
//        assertEquals("Speler mag maximaal 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//
//    //Heb voor kamers dezelfde testen gemaakt om te checken of je bij andere kamers ook tot de maximale aantal hint tegenloopt.
//    //Kamer planning
//    @Test
//    public void checkKamerPlanningOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordPlanning = new AntwoordPlanning();
//        Kamer kamerPlanning = new KamerPlanning(antwoordPlanning);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerPlanning, speler);
//        }
//
//        kamerBetreed.betreed(kamerPlanning, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//    //Kamer Sprint Review
//    @Test
//    public void checkKamerReviewOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordReview = new AntwoordReview();
//        Kamer kamerReview = new KamerReview(antwoordReview);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerReview, speler);
//        }
//
//        kamerBetreed.betreed(kamerReview, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//    //Kamer Daily Scrum
//    @Test
//    public void checkKamerDailyScrumOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordDailyScrum = new AntwoordDailyScrum();
//        Kamer kamerDailyScrum = new KamerDailyScrum(antwoordDailyScrum);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerDailyScrum, speler);
//        }
//
//        kamerBetreed.betreed(kamerDailyScrum, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//    //Kamer RetroSpective
//    @Test
//    public void checkKamerRetroSpectiveOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordRetrospective = new AntwoordRetrospective();
//        Kamer kamerRetrospective = new KamerRetrospective(antwoordRetrospective);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerRetrospective, speler);
//        }
//
//        kamerBetreed.betreed(kamerRetrospective, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//
//    // Kamer Scrumboard
//    @Test
//    public void checkKamerScrumBoardOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordScrumBoard = new AntwoordScrumBoard();
//        Kamer kamerScrumBoard = new KamerScrumBoard(antwoordScrumBoard);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerScrumBoard, speler);
//        }
//
//        kamerBetreed.betreed(kamerScrumBoard, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//
//    //Kamer Finale TIA
//    @Test
//    public void checkKamerFinaleTIAOfHintGebruiktWordt() {
//        // Arrange
//        Antwoord antwoordFinaleTIA = new AntwoordFinaleTIA();
//        Kamer kamerFinaleTIA = new KamerFinaleTIA(antwoordFinaleTIA);
//        KamerBetreed kamerBetreed = new KamerBetreed();
//        Speler speler = new Speler();
//
//        // Act
//        for (int i = 0; i < 4; i++) {
//            speler.voegJokerToe(new HintJoker("hint"));
//            kamerBetreed.verwerkJoker(kamerFinaleTIA, speler);
//        }
//
//        kamerBetreed.betreed(kamerFinaleTIA, speler);
//
////        // Assert
//        assertEquals("Speler mag maar 4 hints gebruiken", 4, speler.getHintCounter());
//        assertEquals("Er mogen geen hints meer over zijn", 0, speler.getHintsLeft());
//    }
//}