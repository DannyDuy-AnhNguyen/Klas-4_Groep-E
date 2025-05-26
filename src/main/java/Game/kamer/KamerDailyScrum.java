package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.HintContext;
import Game.hint.HelpHint;
import Game.hint.FunnyHint;

import java.util.Scanner;

public class KamerDailyScrum extends Kamer {
    private final Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private Scanner scanner;


    public KamerDailyScrum(Antwoord antwoordStrategie) {
        super("Daily Scrum");
        this.antwoordStrategie = antwoordStrategie;
        this.scanner = new Scanner(System.in);
        deur.setOpen(false);
        toonHint();
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Scrum kent maar een paar officiële rollen."));
        hintContext.voegHintToe(0, new FunnyHint("De projectleider zit waarschijnlijk koffie te drinken ergens."));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Een sprint is bedoeld om snel resultaat te boeken."));
        hintContext.voegHintToe(1, new FunnyHint("Als je denkt aan jaren... denk kleiner. Véél kleiner."));
    }

    @Override
    public void betreedIntro() {
        System.out.println("\nJe bent nu in de kamer: " + naam);
        deur.toonStatus();
        System.out.println();

        System.out.println("📦 Items in deze kamer:");
        if (items.isEmpty()) {
            System.out.println("- Geen items beschikbaar.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i));
            }
        }
        System.out.println();
    }

    @Override
    public void verwerkFeedback(int vraagIndex) {
        switch (vraagIndex) {
            case 0 -> System.out.println("Een projectleider is geen officiële rol binnen Scrum.");
            case 1 -> System.out.println("De meeste sprints duren 1 tot 4 weken. Kort genoeg om snel te kunnen bijsturen.");
        }
    }

    @Override
    public void verwerkOpdracht(int vraagIndex) {
        if (vraagIndex == 0) {
            System.out.println("Vraag 1: Welke van de volgende rollen bestaat niet binnen Scrum?");
            System.out.println("a) Projectleider");
            System.out.println("b) Scrum Master");
            System.out.println("c) Development Team");
            System.out.println("d) Product Owner");
        } else if (vraagIndex == 1) {
            System.out.println("Vraag 2: Hoelang duurt een standaard sprint meestal?");
            System.out.println("a) 1 tot 4 weken");
            System.out.println("b) 1 tot 4 maanden");
            System.out.println("c) 1 tot 4 dagen");
            System.out.println("d) 1 tot 4 jaren");
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler) {
        if (correct) {
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("\n✅ Correct! Je krijgt 10 punten.\n");

            if (huidigeVraag == 2) {
                setVoltooid();
                deur.setOpen(true);
                speler.voegVoltooideKamerToe(2);
                System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
            }

        } else {
            speler.voegMonsterToe("Verlies van Focus");
            System.out.println("\n❌ Fout! Monster 'Verlies van Focus' verschijnt! Probeer het opnieuw.\n");

            System.out.println("Wil je een hint? Type 'ja' of 'nee'");
            String antwoord = scanner.nextLine().trim().toLowerCase();

            if(antwoord.equals("ja")){
                // 👇 Toon een hint
                hintContext.toonWillekeurigeHint(huidigeVraag);
            }else


            System.out.println("Probeer het opnieuw.\n");
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
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println((i + 1) + ") " + items.get(i));
                    }
                }
                System.out.println();
            } else if (antwoord.startsWith("pak ")) {
                String itemInput = antwoord.substring(4).trim();
                Item gekozenItem = null;

                try {
                    int index = Integer.parseInt(itemInput) - 1;
                    if (index >= 0 && index < items.size()) {
                        gekozenItem = items.remove(index);
                    } else {
                        System.out.println("❌ Ongeldig itemnummer.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    gekozenItem = neemItem(itemInput);
                }

                if (gekozenItem != null) {
                    speler.voegItemToe(gekozenItem);
                } else if (!itemInput.matches("\\d+")) {
                    System.out.println("❌ Dat item is niet gevonden in deze kamer.");
                }

                System.out.println();
            } else if (antwoord.equals("naar andere kamer")) {
                System.out.println("Je verlaat deze kamer.\n");
                return;
            } else if (antwoord.matches("[a-d]")) {
                boolean antwoordCorrect = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
                verwerkResultaat(antwoordCorrect, speler);
            } else {
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'd', 'status', 'check', 'help', 'pak [item]' of 'naar andere kamer'.\n");
            }
        }

        if (!isVoltooid()) {
            setVoltooid();
            deur.setOpen(true);
            speler.voegVoltooideKamerToe(2);
            System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
        }
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

    public void reset() {
        huidigeVraag = 0;
        deur.setOpen(false);
    }
}