package Game.monster;

import Game.item.Item;
import Game.core.Speler;

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
            System.out.println("(Monster) Vraag 1: Wie bepaalt hoe het werk wordt uitgevoerd tijdens de sprint?");
            System.out.println("a) De Scrum Master");
            System.out.println("b) De Product Owner");
            System.out.println("c) Het Development Team");
        } else if (huidigeVraag == 1) {
            System.out.println("(Monster) Vraag 2: Wie bepaalt wat er tijdens de sprint wordt opgepakt?");
            System.out.println("a) De Product Owner");
            System.out.println("b) De Scrum Master");
            System.out.println("c) Het Development Team");
        } else if (huidigeVraag == 2) {
            System.out.println("(Monster) Vraag 3: Wat wordt er meestal aan het einde van de Sprint Planning vastgelegd?");
            System.out.println("a) De epics voor het hele project");
            System.out.println("b) Het Sprint Doel en het geselecteerde werk");
            System.out.println("c) De uren die iedereen werkt");
        } else if (huidigeVraag == 3) {
            System.out.println("(Monster) Vraag 4: Wat is een belangrijk uitgangspunt bij het plannen van een sprint?");
            System.out.println("a) Alleen de Scrum Master mag taken toewijzen aan teamleden.");
            System.out.println("b) Het Development Team schat zelf het werk in dat ze kunnen voltooien.");
            System.out.println("c) De Product Owner bepaalt hoeveel werk het team oppakt.");
            System.out.println("d) Het werk wordt willekeurig gekozen uit de product backlog.");
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