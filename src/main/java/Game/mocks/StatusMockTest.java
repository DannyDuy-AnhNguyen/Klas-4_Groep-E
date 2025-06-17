package Game.mocks;

import Game.core.Speler;      // Voor de Speler-klasse

public class StatusMockTest {
    public static void main(String[] args) {
        Speler speler = new Speler();

        MockObserver mock = new MockObserver();

        speler.voegObserverToe(mock);
        System.out.println("Je krijgt 10 punten erbij!");
        speler.verhoogScore(10);
        speler.verliesLeven();

        if (mock.aantalUpdates == 2) {
            System.out.println("✅ Observer werd twee keer aangeroepen.");
        } else {
            System.out.println("❌ Observer werd " + mock.aantalUpdates + " keer aangeroepen (verwacht: 2).");
        }
    }
}

//interacties checken