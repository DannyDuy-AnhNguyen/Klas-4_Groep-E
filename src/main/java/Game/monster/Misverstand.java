package Game.monster;

import Game.item.Item;
import Game.core.Speler;
import Game.core.TextPrinter;

public class Misverstand extends Monster implements MonsterType {
    private final String[] juisteAntwoorden = {
            "c", // Vraag 1
            "c", // Vraag 2
            "b", // Vraag 3
            "b"  // Vraag 4
    };

    public Misverstand() {
        super(
                "Misverstand",
                "Een monster dat ontstaat door onduidelijke communicatie tijdens de Sprint Planning.",
                "Wat is het doel van een Sprint Planning?\nA) Het plannen van vakantie\nB) Het bepalen van het werk voor de Sprint\nC) Het kiezen van een Scrum Master",
                "b"
        );
    }

    @Override
    public boolean verslaMetItem(Item item) {
        return false; // Wordt nog uitgewerkt
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            TextPrinter.print("(Monster) Vraag 1: Wie bepaalt hoe het werk wordt uitgevoerd tijdens de sprint?");
            TextPrinter.print("a) De Scrum Master");
            TextPrinter.print("b) De Product Owner");
            TextPrinter.print("c) Het Development Team");
        } else if (huidigeVraag == 1) {
            TextPrinter.print("(Monster) Vraag 2: Wie bepaalt wat er tijdens de sprint wordt opgepakt?");
            TextPrinter.print("a) De Product Owner");
            TextPrinter.print("b) De Scrum Master");
            TextPrinter.print("c) Het Development Team");
        } else if (huidigeVraag == 2) {
            TextPrinter.print("(Monster) Vraag 3: Wat wordt er meestal aan het einde van de Sprint Planning vastgelegd?");
            TextPrinter.print("a) De epics voor het hele project");
            TextPrinter.print("b) Het Sprint Doel en het geselecteerde werk");
            TextPrinter.print("c) De uren die iedereen werkt");
        } else if (huidigeVraag == 3) {
            TextPrinter.print("(Monster) Vraag 4: Wat is een belangrijk uitgangspunt bij het plannen van een sprint?");
            TextPrinter.print("a) Alleen de Scrum Master mag taken toewijzen aan teamleden.");
            TextPrinter.print("b) Het Development Team schat zelf het werk in dat ze kunnen voltooien.");
            TextPrinter.print("c) De Product Owner bepaalt hoeveel werk het team oppakt.");
            TextPrinter.print("d) Het werk wordt willekeurig gekozen uit de product backlog.");
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