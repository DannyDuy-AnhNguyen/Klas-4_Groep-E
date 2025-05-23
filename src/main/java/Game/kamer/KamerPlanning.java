package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;

import java.util.Scanner;

public class KamerPlanning extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;

    public KamerPlanning(Antwoord antwoordStrategie) {
        super("Sprint Planning");
        this.antwoordStrategie = antwoordStrategie;
        deur.setOpen(false);
        toonHint();
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Bij de Sprint Planning doet iedereen mee."));
        hintContext.voegHintToe(0, new FunnyHint("Sprint Planning is het plannen met iedereen om .. een dorp aan te vallen."));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("In de sprint planning wordt gekeken wat het doel de aankomende sprint"));
        hintContext.voegHintToe(1, new FunnyHint("Om een doelpunt te maken moet je sprinten naar het doel om een doelpunt te scoren."));
    }

    @Override
    public void betreedIntro() {
        System.out.println();
        System.out.println("Je bent nu in de kamer: " + naam);
        deur.toonStatus();
        System.out.println();
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Bij de sprintplanning nemen alle betrokkenen deel aan wat er gepland gaat worden.");
        } else if (huidigeVraag == 1) {
            System.out.println("Tijdens de sprintplanning wordt vastgesteld wat het doel van de sprint is en welke backlog-items worden opgepakt.");
            System.out.println("Het team bepaalt ook de moeilijkheidsgraad van user stories met behulp van Sprint Poker.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag){
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
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler){
        if (correct) {
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("\n✅ Correct! Je krijgt 10 punten.\n");
        } else {
            speler.voegMonsterToe("Misverstand");
            System.out.println("\n❌ Fout! Monster 'Misverstand' verschijnt! Probeer het opnieuw.\n");

            // 👇 Toon een hint
            hintContext.toonWillekeurigeHint(huidigeVraag);
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

        while (huidigeVraag < 2) {
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
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'd', 'status', 'check', 'help' of 'naar andere kamer'.\n");
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
        System.out.println("Gebruik 'check' om items in deze kamer te bekijken.");
        System.out.println("Gebruik 'help' om deze hulp te zien.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
    }
}
