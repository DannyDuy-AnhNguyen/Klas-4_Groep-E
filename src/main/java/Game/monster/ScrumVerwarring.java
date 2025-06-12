package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;
import Game.core.TextPrinter;

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
            TextPrinter.print("(Monster) Vraag 3: Wat zorgt het Scrumboard voor?");
            TextPrinter.print("a) Voor klanten/stakeholders om de voortgang te volgen.");
            TextPrinter.print("b) Voor overzicht en duidelijkheid.");
            TextPrinter.print("c) Voor wat, wie en waar iemand van het Development team mee bezig is.");
            TextPrinter.print("d) Alle bovenstaande vragen");
        } else if (huidigeVraag == 1) {
            TextPrinter.print("(Monster) Vraag 4: Welke antwoord is onjuist in “Hoe werk je samen in een team met een Scrumboard?”");
            TextPrinter.print("a) Bij het bord userstories toevoegen.");
            TextPrinter.print("b) De userstories de taken eraan koppelen.");
            TextPrinter.print("c) Bij het bord wordt ook epics toegevoegd als kaart.");
            TextPrinter.print("d) Het bord up to date houden waar iedereen is.");
        } else if (huidigeVraag == 2) {
            TextPrinter.print("(Monster) Vraag 5: Wie is verantwoordelijk voor het up-to-date houden van het Scrumboard?");
            TextPrinter.print("a) Minder communicatie nodig");
            TextPrinter.print("b) Duidelijkheid over wie waaraan werkt");
            TextPrinter.print("c) Niemand hoeft iets te plannen");
        } else if (huidigeVraag == 3) {
            TextPrinter.print("(Monster) Vraag 6: Wat is een nadeel van een goed bijgehouden Scrumboard?");
            TextPrinter.print("a) Transparantie over voortgang");
            TextPrinter.print("b) Minder meetings nodig");
            TextPrinter.print("c) Meer bureaucratie regels");
            TextPrinter.print("d) Sneller kunnen reageren op blokkades");
        } else {
            TextPrinter.print("Er is geen vraag beschikbaar voor dit nummer.");
        }
    }

    public void verliesLeven(Speler speler) {
        speler.verliesLeven();
        TextPrinter.print("Je hebt een leven verloren door het monster Scrum Verwarring!");
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