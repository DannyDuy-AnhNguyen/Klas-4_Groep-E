package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;

import java.util.Scanner;

public class KamerReview extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private Status status;

    public KamerReview(Antwoord antwoordStrategie) {
        super("Sprint Review");
        deur.setOpen(false);
        this.antwoordStrategie = antwoordStrategie;
    }

    @Override
    public void betreedIntro(){
        System.out.println();
        System.out.println("Je bent nu in de kamer: " + naam);
        deur.toonStatus();
        System.out.println();
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        switch (huidigeVraag) {
            case 0 -> System.out.println("Sprintreview wordt met je product owner gehouden om te kijken waar de scrumteam tot nu toe uitgevoerd heeft en of het voldaan is.");
            case 1 -> System.out.println("Sprint Review zorgt ervoor dat de scrumteam de feedback verzameld en wat de Product Owner nu van het product vindt.");
            case 2 -> System.out.println("Dankzij Sprint Review weten beide kanten zowel de product owner als scrumteam wat er gedaan kan worden en wat er verwacht wordt.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag){
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("Wanneer wordt er een sprintreview gehouden?");
                System.out.println("a) Aan het begin van de sprint");
                System.out.println("b) Tijdens de sprint");
                System.out.println("c) Aan het einde van de sprint");
            }
            case 1 -> {
                System.out.println("Wat is het belangrijkste doel van de Sprint Review?");
                System.out.println("a) De Scrum Master evalueren");
                System.out.println("b) Het increment inspecteren en feedback verzamelen");
                System.out.println("c) De volgende sprint alvast plannen");
                System.out.println("d) Vorige sprint doornemen");
            }
            case 2 -> {
                System.out.println("De voordelen van een Sprint Review zijn...?");
                System.out.println("a) Meer vergaderingen = meer productiviteit");
                System.out.println("b) Transparantie, snelle feedback, alignment met stakeholders");
                System.out.println("c) Langer werken zonder pauzes");
                System.out.println("d) De product owner tevreden houden");
            }
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler){
        if (correct) {
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("Correct!\n");
        } else {
            speler.voegMonsterToe("Sprint Confusie");
            System.out.println("Monster 'Sprint Confusie' verschijnt! Probeer het opnieuw.\n");
        }
    }

    @Override
    public void betreed(Speler speler) {
        if (!deur.isOpen()) {
            System.out.println("🚪 De deur is gesloten, je kunt deze kamer nog niet betreden.");
            deur.toonStatus();
            return;
        }

        this.status = new Status(speler);
        Scanner scanner = new Scanner(System.in);

        betreedIntro();

        while (huidigeVraag < 3) {
            verwerkOpdracht(huidigeVraag);

            String antwoord = scanner.nextLine().trim().toLowerCase();

            if (antwoord.equals("help")) {
                toonHelp();
                System.out.println();
            } else if (antwoord.equals("status")) {
                status.update(speler);
                System.out.println();
            } else if (antwoord.equals("check")) {
                if (items.isEmpty()) {
                    System.out.println("📦 Geen items in deze kamer.");
                } else {
                    System.out.println("📦 Items in deze kamer:");
                    for (Item item : items) {
                        System.out.println("- " + item);
                    }
                }
                System.out.println();
            } else if (antwoord.equals("naar andere kamer")) {
                System.out.println("Je verlaat deze kamer.\n");
                return;
            } else if (antwoord.matches("[a-d]")) {
                boolean antwoordCorrect = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
                verwerkResultaat(antwoordCorrect, speler);
            } else {
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'd', 'status', 'help', 'check' of 'naar andere kamer'.\n");
            }
        }

        setVoltooid();
        deur.setOpen(true);
        System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
        speler.voegVoltooideKamerToe(2);
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return false;
    }

    @Override
    public void toonHelp() {
        System.out.println("Typ het letterantwoord: a, b, c of d");
        System.out.println("Gebruik 'status' om je huidige status te zien.");
        System.out.println("Gebruik 'help' om deze hulp te zien.");
        System.out.println("Gebruik 'check' om items in deze kamer te bekijken.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
    }
}
