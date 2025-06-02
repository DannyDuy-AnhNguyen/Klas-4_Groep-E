package Game.monster;

import Game.item.Item;
import Game.core.Speler;

import java.util.Scanner;

public class Misverstand extends Monster {
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
            System.out.println();
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

    // ** Toegevoegde methode **
    public String getJuisteAntwoord(int index) {
        if (index >= 0 && index < juisteAntwoorden.length) {
            return juisteAntwoorden[index];
        }
        return "";
    }
}