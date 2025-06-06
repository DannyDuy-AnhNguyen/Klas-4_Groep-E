package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;

public class BlameGame extends Monster {
    private final String[] juisteAntwoorden = {
            "c", // Vraag 3
            "e", // Vraag 4
            "b", // Vraag 5
            "c"  // Vraag 6
    };

    public BlameGame() {
        super(
                "Blame Game",
                "Een monster dat ontstaat wanneer teamleden elkaar beschuldigen in plaats van samen te reflecteren tijdens de Sprint Retrospective.",
                "Wat is het doel van een Sprint Retrospective?\nA) Elkaar beschuldigen\nB) De burndown chart bespreken\nC) Samenwerking en verbetering bespreken",
                "c"
        );
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Eventueel later uit te breiden
    }

    public void verwerkOpdracht(int huidigeVraag) {
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("(Monster) Vraag 3: Wie neemt er deel aan de Sprint Retrospective?");
                System.out.println("a) Alleen de Scrum Master");
                System.out.println("b) De Product Owner en Stakeholders");
                System.out.println("c) Het hele Scrum Team (Scrum Master, Product Owner en Development Team)");
            }
            case 1 -> {
                System.out.println("(Monster) Vraag 4: Wat wordt besproken tijdens de Sprint Retrospective?");
                System.out.println("a) Wat er goed ging");
                System.out.println("b) Wat elk teamlid vindt wat er beter kan.");
                System.out.println("c) Wat er beter kon");
                System.out.println("d) Hoe het wordt verbeterd");
                System.out.println("e) Elk antwoord is juist");
            }
            case 2 -> {
                System.out.println("(Monster) Vraag 5: Bij een Sprint Retrospective wordt een code review gehouden?");
                System.out.println("a) Juist");
                System.out.println("b) Onjuist");
            }
            case 3 -> {
                System.out.println("(Monster) Vraag 6: Wat is geen geschikt onderwerp voor de Sprint Retrospective?");
                System.out.println("a) Samenwerking binnen het team");
                System.out.println("b) Technieken die goed of slecht werken");
                System.out.println("c) Voortgang van individuele user stories");
                System.out.println("d) Communicatieproblemen of successen");
            }
            default -> System.out.println("Er is geen vraag beschikbaar voor dit nummer.");
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