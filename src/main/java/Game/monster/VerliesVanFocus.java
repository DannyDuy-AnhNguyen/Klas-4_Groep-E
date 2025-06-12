package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;
import Game.core.TextPrinter;

public class VerliesVanFocus extends Monster {
    private final String[] juisteAntwoorden = {
            "c", // Vraag 3
            "b", // Vraag 4
            "b", // Vraag 5
            "c"  // Vraag 6
    };

    public VerliesVanFocus() {
        super(
                "Verlies van Focus",
                "Een monster dat ontstaat wanneer het team tijdens de Daily Scrum afdwaalt van het sprintdoel.",
                "Wat gebeurt er tijdens een goede Daily Scrum?\nA) De Scrum Master controleert of iedereen hard genoeg werkt\nB) Teamleden rapporteren aan de Product Owner\nC) Teamleden stemmen het werk op elkaar af richting het sprintdoel\nD) Het team plant de hele sprint opnieuw",
                "c"
        );
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Nog geen itemspecifieke logica
    }

    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            TextPrinter.print("(Monster) Vraag 3: Welke drie rollen zijn er in Scrum?");
            TextPrinter.print("a) Manager, Ontwikkelaar, Tester");
            TextPrinter.print("b) Klant, Projectleider, Ontwerper");
            TextPrinter.print("c) Product Owner, Scrum Master, Development Team");
        } else if (huidigeVraag == 1) {
            TextPrinter.print("(Monster) Vraag 4: Wat is een kenmerk van werken met Scrum?");
            TextPrinter.print("a) Alles wordt vooraf volledig gepland");
            TextPrinter.print("b) Er wordt gewerkt in korte iteraties, genaamd sprints");
            TextPrinter.print("c) Alleen de Scrum Master beslist wat er gedaan wordt");
        } else if (huidigeVraag == 2) {
            TextPrinter.print("(Monster) Vraag 5: Wat gebeurt er aan het einde van elke sprint?");
            TextPrinter.print("a) Het team gaat op vakantie.");
            TextPrinter.print("b) De sprint wordt geëvalueerd en het product wordt mogelijk opgeleverd.");
            TextPrinter.print("c) De product backlog wordt verwijderd.");
        } else if (huidigeVraag == 3) {
            TextPrinter.print("(Monster) Vraag 6: Wat gebeurt er tijdens een goede Daily Scrum?");
            TextPrinter.print("a) De Scrum Master controleert of iedereen hard genoeg werkt");
            TextPrinter.print("b) Teamleden rapporteren aan de Product Owner");
            TextPrinter.print("c) Teamleden stemmen het werk op elkaar af richting het sprintdoel");
            TextPrinter.print("d) Het team plant de hele sprint opnieuw");
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