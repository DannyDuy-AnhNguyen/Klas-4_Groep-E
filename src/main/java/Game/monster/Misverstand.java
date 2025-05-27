package Game.monster;

import Game.core.Item;
import Game.core.Speler;

import java.util.Scanner;

public class Misverstand extends Monster {
    private final String[] juisteAntwoorden = {
            "d", // Vraag 1
            "b", // Vraag 2
            "b", // Vraag 3
            "c"  // Vraag 4
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

    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Vraag 1: Wie neemt deel aan de Sprint Planning?");
            System.out.println("a) Alleen de Scrum Master");
            System.out.println("b) Product Owner en Scrum Master");
            System.out.println("c) Product Owner, Scum Master en het hele Development Team");
            System.out.println("d) Product Owner, Scrum Master en het hele Development Team");
        } else if (huidigeVraag == 1) {
            System.out.println("Vraag 2: Wat wordt er tijdens de Sprint Planning vastgesteld?");
            System.out.println("a) Welke teamleden vakantie hebben");
            System.out.println("b) Wat het doel van de sprint is en welke backlog-items worden opgepakt");
            System.out.println("c) Hoe de vorige sprint geëvalueerd is");
            System.out.println("d) Wat de vastgestelde items van de backlog zijn als de product owner tevreden is met het product");
        } else if (huidigeVraag == 2) {
            System.out.println("Vraag 3: Wat is het doel van Sprint Planning?");
            System.out.println("a) Het plannen van vakantie");
            System.out.println("b) Het bepalen van het werk voor de Sprint");
            System.out.println("c) Het kiezen van een Scrum Master");
            System.out.println("d) Het bespreken van feedback van stakeholders");
        } else if (huidigeVraag == 3) {
            System.out.println("Vraag 4: Wie bepaalt hoe het werk wordt uitgevoerd tijdens de sprint?");
            System.out.println("a) De Scrum Master");
            System.out.println("b) De Product Owner");
            System.out.println("c) Het Development Team");
            System.out.println("d) De stakeholders");
        } else {
            System.out.println("Er is geen vraag beschikbaar voor dit nummer.");
        }
    }

    public boolean verslaMetExtraVragen() {
        Scanner scanner = new Scanner(System.in);
        int correct = 0;

        System.out.println("Je hebt geen geschikt item. Je moet nu extra vragen correct beantwoorden om het monster te verslaan!");

        for (int i = 0; i < juisteAntwoorden.length; i++) {
            verwerkOpdracht(i);
            System.out.print("Jouw antwoord: ");
            String antwoord = scanner.nextLine().trim().toLowerCase();

            if (antwoord.equals(juisteAntwoorden[i])) {
                System.out.println("Correct!");
                correct++;
            } else {
                System.out.println("Fout. Het juiste antwoord was: " + juisteAntwoorden[i].toUpperCase());
            }
        }

        if (correct == juisteAntwoorden.length) {
            verslagen = true;
            System.out.println("Je hebt alle extra vragen correct beantwoord! Het monster is verslagen!");
            return true;
        } else {
            System.out.println("Niet alle antwoorden waren correct. Het monster leeft nog!");
            return false;
        }
    }

    public boolean verslaMetExtraVragen(int aantalVragen, Speler speler) {
        Scanner scanner = new Scanner(System.in);
        int fouten = 0;

        System.out.println("Je moet nu " + aantalVragen + " extra vraag(en) goed beantwoorden om het monster te verslaan.");

        for (int i = 0; i < aantalVragen && i < juisteAntwoorden.length; i++) {
            verwerkOpdracht(i);
            System.out.print("Jouw antwoord: ");
            String antwoord = scanner.nextLine().trim().toLowerCase();

            if (antwoord.equals(juisteAntwoorden[i])) {
                System.out.println("Correct!");
            } else {
                fouten++;
                speler.verliesLeven();
                System.out.println("Fout! Het juiste antwoord was: " + juisteAntwoorden[i].toUpperCase());
                System.out.println("Je verliest een leven. Nog " + speler.getLevens() + " over.");
            }
        }

        if (fouten == 0) {
            verslagen = true;
            System.out.println("Je hebt alle extra vragen goed! Het monster is verslagen.");
            return true;
        } else {
            System.out.println("Je had " + fouten + " fout(en). Het monster is nog niet verslagen.");
            return false;
        }
    }

    public void verliesLeven(Speler speler) {
        speler.verliesLeven();
        System.out.println("Je hebt een leven verloren door het monster Misverstand!");
    }
}