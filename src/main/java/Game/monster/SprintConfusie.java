package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;

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
            System.out.println("(Monster) Vraag 4: Wat doe je tijdens een Sprint Review?");
            System.out.println("a) Alleen reviewen hoe jij zelf de vorige sprint vindt gaan.");
            System.out.println("b) Gezamenlijk reviewen hoe jij zelf de vorige sprint vindt gaan.");
            System.out.println("c) Gezamenlijk reviewen hoe jij en teamleden de vorige sprint vindt gaan.");
            System.out.println("d) Totaal geen idee😭");
        } else if (huidigeVraag == 1) {
            System.out.println("(Monster) Vraag 5: Wie zijn er doorgaans aanwezig bij een Sprint Review?");
            System.out.println("a) Alleen het Development Team");
            System.out.println("b) Alleen de Product Owner en Scrum Master");
            System.out.println("c) Het Scrum Team en relevante stakeholders");
            System.out.println("d) Alleen het Scrum Team");
        } else if (huidigeVraag == 2) {
            System.out.println("(Monster) Vraag 6: Wat wordt tijdens een Sprint Review gepresenteerd?");
            System.out.println("a) Een PowerPoint met alleen theoretische ideeën");
            System.out.println("b) De productvisie voor het komende jaar");
            System.out.println("c) Een werkend increment dat \"Done\" is");
            System.out.println("d) Alleen plannen voor de volgende sprint");
        } else if (huidigeVraag == 3) {
            System.out.println("(Monster) Vraag 7: Wat is een mogelijk gevolg van een goede Sprint Review?");
            System.out.println("a) Het team hoeft geen Retrospective meer te doen");
            System.out.println("b) Stakeholders krijgen beter inzicht en kunnen input geven");
            System.out.println("c) De Sprint wordt verlengd");
        } else {
            System.out.println("Er is geen vraag beschikbaar voor dit nummer.");
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