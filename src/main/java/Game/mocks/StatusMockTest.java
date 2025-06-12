package Game.mocks;

import Game.core.Speler;      // Voor de Speler-klasse

public class StatusMockTest {
    public static void main(String[] args) {
        Speler speler = new Speler();
        speler.setNaam("Tester"); // Zorg dat deze setter bestaat

        MockObserver mock = new MockObserver();

        speler.voegObserverToe(mock);
        speler.verhoogScore(10);  // Zou de observer moeten triggeren

        if (mock.isBijgewerkt) {
            System.out.println("✅ Observer werd aangeroepen");
        } else {
            System.out.println("❌ Observer werd NIET aangeroepen");
        }
    }
}