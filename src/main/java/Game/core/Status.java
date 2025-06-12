package Game.core;

public class Status implements Observer {
    private Speler speler;

    public Status(Speler speler) {
        this.speler = speler;
        speler.voegObserverToe(this);  // toevoegen als observer
    }

    @Override
    public void update(Speler speler) {
        // eventueel logica voor status bijwerken, maar GEEN print
        // deze methode wordt stil uitgevoerd op wijzigingen
    }

    public void toonStatus() {
        TextPrinter.print("\n=== STATUS ===");
        System.out.println("Speler: " + speler.getNaam());
        System.out.println("Score: " + speler.getScore());
        System.out.println("Voltooide kamers: " + speler.getVoltooideKamers().size());
        if (!speler.getMonsters().isEmpty()) {
            System.out.println("Actieve monsters: " + speler.getMonsters());
        } else {
            TextPrinter.print("Geen actieve monsters.");
        }
        //Deze system print laat zien hoeveel hints de speler gebruikt heeft. De speler kan maximaal 4 hints gebruiken.
        System.out.println("Aantal gebruikte hints: " + speler.getHintCounter());
        TextPrinter.print("==================\n");
    }
}