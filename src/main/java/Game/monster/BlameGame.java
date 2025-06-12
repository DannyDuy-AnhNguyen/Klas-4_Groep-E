package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;
import Game.core.TextPrinter;

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
                TextPrinter.print("(Monster) Vraag 3: Wie neemt er deel aan de Sprint Retrospective?");
                TextPrinter.print("a) Alleen de Scrum Master");
                TextPrinter.print("b) De Product Owner en Stakeholders");
                TextPrinter.print("c) Het hele Scrum Team (Scrum Master, Product Owner en Development Team)");
            }
            case 1 -> {
                TextPrinter.print("(Monster) Vraag 4: Wat wordt besproken tijdens de Sprint Retrospective?");
                TextPrinter.print("a) Wat er goed ging");
                TextPrinter.print("b) Wat elk teamlid vindt wat er beter kan.");
                TextPrinter.print("c) Wat er beter kon");
                TextPrinter.print("d) Hoe het wordt verbeterd");
                TextPrinter.print("e) Elk antwoord is juist");
            }
            case 2 -> {
                TextPrinter.print("(Monster) Vraag 5: Bij een Sprint Retrospective wordt een code review gehouden?");
                TextPrinter.print("a) Juist");
                TextPrinter.print("b) Onjuist");
            }
            case 3 -> {
                TextPrinter.print("(Monster) Vraag 6: Wat is geen geschikt onderwerp voor de Sprint Retrospective?");
                TextPrinter.print("a) Samenwerking binnen het team");
                TextPrinter.print("b) Technieken die goed of slecht werken");
                TextPrinter.print("c) Voortgang van individuele user stories");
                TextPrinter.print("d) Communicatieproblemen of successen");
            }
            default -> TextPrinter.print("Er is geen vraag beschikbaar voor dit nummer.");
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