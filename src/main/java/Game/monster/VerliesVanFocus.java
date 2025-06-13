package Game.monster;

import Game.item.Item;
import Game.core.Speler;
import Game.monster.MonsterHelper;

import java.util.Scanner;

public class VerliesVanFocus extends Monster {
    private final String[] juisteAntwoorden = {
            "c", // Vraag 3
            "b", // Vraag 4
            "b", // Vraag 5
            "c"  // Vraag 6
    };
    private final MonsterHelper monsterHelper;

    public VerliesVanFocus() {
        super(
                "Verlies van Focus",
                "Een monster dat ontstaat wanneer het team tijdens de Daily Scrum afdwaalt van het sprintdoel.",
                "Wat gebeurt er tijdens een goede Daily Scrum?\nA) De Scrum Master controleert of iedereen hard genoeg werkt\nB) Teamleden rapporteren aan de Product Owner\nC) Teamleden stemmen het werk op elkaar af richting het sprintdoel\nD) Het team plant de hele sprint opnieuw",
                "c"
        );
        this.monsterHelper = new MonsterHelper(juisteAntwoorden);
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Nog geen itemspecifieke logica
    }

    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("(Monster) Vraag 3: Welke drie rollen zijn er in Scrum?");
            System.out.println("a) Manager, Ontwikkelaar, Tester");
            System.out.println("b) Klant, Projectleider, Ontwerper");
            System.out.println("c) Product Owner, Scrum Master, Development Team");
        } else if (huidigeVraag == 1) {
            System.out.println("(Monster) Vraag 4: Wat is een kenmerk van werken met Scrum?");
            System.out.println("a) Alles wordt vooraf volledig gepland");
            System.out.println("b) Er wordt gewerkt in korte iteraties, genaamd sprints");
            System.out.println("c) Alleen de Scrum Master beslist wat er gedaan wordt");
        } else if (huidigeVraag == 2) {
            System.out.println("(Monster) Vraag 5: Wat gebeurt er aan het einde van elke sprint?");
            System.out.println("a) Het team gaat op vakantie.");
            System.out.println("b) De sprint wordt geëvalueerd en het product wordt mogelijk opgeleverd.");
            System.out.println("c) De product backlog wordt verwijderd.");
        } else if (huidigeVraag == 3) {
            System.out.println("(Monster) Vraag 6: Wat gebeurt er tijdens een goede Daily Scrum?");
            System.out.println("a) De Scrum Master controleert of iedereen hard genoeg werkt");
            System.out.println("b) Teamleden rapporteren aan de Product Owner");
            System.out.println("c) Teamleden stemmen het werk op elkaar af richting het sprintdoel");
            System.out.println("d) Het team plant de hele sprint opnieuw");
        } else {
            System.out.println("Er is geen vraag beschikbaar voor dit nummer.");
        }
    }

    @Override
    public String getJuisteAntwoord(int index) {
        return monsterHelper.getJuisteAntwoord(index);
    }
}