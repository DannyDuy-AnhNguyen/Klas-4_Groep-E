package Game.core;

public class Status implements Observer {
    private Speler speler;

    public Status(Speler speler) {
        this.speler = speler;
        speler.voegObserverToe(this);  // toevoegen als observer
    }

    @Override
    public void update(Speler speler) {
        toonStatus();
    }

    public void toonStatus() {
        System.out.println("\n=== STATUS ===");
        System.out.println("Speler: " + speler.getNaam());
        System.out.println("Score: " + speler.getScore());
        System.out.println("Voltooide kamers: " + speler.getVoltooideKamers().size());
        if (!speler.getMonsters().isEmpty()) {
            System.out.println("Actieve monsters: " + speler.getMonsters());
        } else {
            System.out.println("Geen actieve monsters.");
        }
        System.out.println("==================\n");
    }
}