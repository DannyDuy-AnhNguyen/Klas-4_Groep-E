package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;

public class ScrumVerwarring extends Monster {
    private final String[] juisteAntwoorden = {
            "d", // Vraag 3
            "c", // Vraag 4
            "b", // Vraag 5
            "c"  // Vraag 6
    };

    public ScrumVerwarring() {
        super(
                "Scrum Verwarring",
                "Een monster dat chaos zaait over het gebruik en nut van het Scrumboard.",
                "Wat zorgt het Scrumboard voor?\nA) Voor klanten/stakeholders om de voortgang te volgen.\nB) Voor overzicht en duidelijkheid.\nC) Voor wat, wie en waar iemand van het Development team mee bezig is.\nD) Alle bovenstaande vragen",
                "d"
        );
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Nog uit te breiden
    }

    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("(Monster) Vraag 3: Wat zorgt het Scrumboard voor?");
            System.out.println("a) Voor klanten/stakeholders om de voortgang te volgen.");
            System.out.println("b) Voor overzicht en duidelijkheid.");
            System.out.println("c) Voor wat, wie en waar iemand van het Development team mee bezig is.");
            System.out.println("d) Alle bovenstaande vragen");
        } else if (huidigeVraag == 1) {
            System.out.println("(Monster) Vraag 4: Welke antwoord is onjuist in “Hoe werk je samen in een team met een Scrumboard?”");
            System.out.println("a) Bij het bord userstories toevoegen.");
            System.out.println("b) De userstories de taken eraan koppelen.");
            System.out.println("c) Bij het bord wordt ook epics toegevoegd als kaart.");
            System.out.println("d) Het bord up to date houden waar iedereen is.");
        } else if (huidigeVraag == 2) {
            System.out.println("(Monster) Vraag 5: Wie is verantwoordelijk voor het up-to-date houden van het Scrumboard?");
            System.out.println("a) Minder communicatie nodig");
            System.out.println("b) Duidelijkheid over wie waaraan werkt");
            System.out.println("c) Niemand hoeft iets te plannen");
        } else if (huidigeVraag == 3) {
            System.out.println("(Monster) Vraag 6: Wat is een nadeel van een goed bijgehouden Scrumboard?");
            System.out.println("a) Transparantie over voortgang");
            System.out.println("b) Minder meetings nodig");
            System.out.println("c) Meer bureaucratie regels");
            System.out.println("d) Sneller kunnen reageren op blokkades");
        } else {
            System.out.println("Er is geen vraag beschikbaar voor dit nummer.");
        }
    }

    public void verliesLeven(Speler speler) {
        speler.verliesLeven();
        System.out.println("Je hebt een leven verloren door het monster Scrum Verwarring!");
    }

    @Override
    public String getJuisteAntwoord(int index) {
        if (index >= 0 && index < juisteAntwoorden.length) {
            return juisteAntwoorden[index];
        } else {
            return ""; // Of eventueel: throw new IllegalArgumentException("Ongeldig index");
        }
    }
}