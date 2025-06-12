package Game.mocks;

import Game.core.TextPrinter;

import Game.core.Speler;      // Voor de Speler-klasse

public class StatusMockTest {
    public static void main(String[] args) {
        Speler speler = new Speler();
        speler.setNaam("Tester"); // Zorg dat deze setter bestaat

        MockObserver mock = new MockObserver();

        speler.voegObserverToe(mock);
        speler.verhoogScore(10);  // Zou de observer moeten triggeren

        if (mock.isBijgewerkt) {
            TextPrinter.print("Observer werd aangeroepen");
        } else {
            TextPrinter.print("Observer werd NIET aangeroepen");
        }
    }
}