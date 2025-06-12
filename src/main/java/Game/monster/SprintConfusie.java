package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;
import Game.core.TextPrinter;

public class SprintConfusie extends Monster {
    private final String[] juisteAntwoorden = {
            "c", // Vraag 4
            "c", // Vraag 5
            "c", // Vraag 6
            "b"  // Vraag 7
    };

    public SprintConfusie() {
        super(
                "Sprint Confusie",
                "Een monster dat verwarring zaait over het doel en de aanpak van de Sprint Review.",
                "Wat is het doel van een Sprint Review?\nA) Controleren of iedereen wel op tijd was\nB) Een update geven aan de Scrum Master\nC) Terugkijken op het increment en feedback verzamelen",
                "c"
        );
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Mogelijkheid tot uitbreiding
    }

    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            TextPrinter.print("(Monster) Vraag 4: Wat doe je tijdens een Sprint Review?");
            TextPrinter.print("a) Alleen reviewen hoe jij zelf de vorige sprint vindt gaan.");
            TextPrinter.print("b) Gezamenlijk reviewen hoe jij zelf de vorige sprint vindt gaan.");
            TextPrinter.print("c) Gezamenlijk reviewen hoe jij en teamleden de vorige sprint vindt gaan.");
            TextPrinter.print("d) Totaal geen idee😭");
        } else if (huidigeVraag == 1) {
            TextPrinter.print("(Monster) Vraag 5: Wie zijn er doorgaans aanwezig bij een Sprint Review?");
            TextPrinter.print("a) Alleen het Development Team");
            TextPrinter.print("b) Alleen de Product Owner en Scrum Master");
            TextPrinter.print("c) Het Scrum Team en relevante stakeholders");
            TextPrinter.print("d) Alleen het Scrum Team");
        } else if (huidigeVraag == 2) {
            TextPrinter.print("(Monster) Vraag 6: Wat wordt tijdens een Sprint Review gepresenteerd?");
            TextPrinter.print("a) Een PowerPoint met alleen theoretische ideeën");
            TextPrinter.print("b) De productvisie voor het komende jaar");
            TextPrinter.print("c) Een werkend increment dat \"Done\" is");
            TextPrinter.print("d) Alleen plannen voor de volgende sprint");
        } else if (huidigeVraag == 3) {
            TextPrinter.print("(Monster) Vraag 7: Wat is een mogelijk gevolg van een goede Sprint Review?");
            TextPrinter.print("a) Het team hoeft geen Retrospective meer te doen");
            TextPrinter.print("b) Stakeholders krijgen beter inzicht en kunnen input geven");
            TextPrinter.print("c) De Sprint wordt verlengd");
        } else {
            TextPrinter.print("Er is geen vraag beschikbaar voor dit nummer.");
        }
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