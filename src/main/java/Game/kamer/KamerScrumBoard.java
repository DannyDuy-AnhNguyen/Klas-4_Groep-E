package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;

import java.util.Scanner;

public class KamerScrumBoard extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private Status status;
    private final HintContext hintContext = new HintContext();


    public KamerScrumBoard(Antwoord antwoordStrategie) {
        super("Scrum Board");
        this.antwoordStrategie = antwoordStrategie;
        deur.setOpen(false);
        toonHint();
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Scrum-proces begint altijd eerst met epic."));
        hintContext.voegHintToe(0, new FunnyHint("Spiegeltje Spiegeltje aan de want, wie is het meest epic van het land?"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Scrumboard begint met de product backlog en dan nog een backlog."));
        hintContext.voegHintToe(1, new FunnyHint("Het product wordt geproduceerd en sprint sneller Usain Bolt. Gas gas gas!"));
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
    public void verwerkFeedback(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Epic is de deelonderdeel van een project onderwerp, Userstory de deel van je Epic en Taken de deel van de je userstory");
        } else if (huidigeVraag == 1) {
            System.out.println("Product backlog is waar alles userstories staan die nog gedaan moet worden,\nSprint backlog de userstories voor de sprint die je in deze sprint wilt maken,\n" +
                    "To Do wat er vandaag gedaan moet worden,\nDoing welke teamlid aan het uitvoeren is\nTesting wat nog getest moet worden\nDone wat er al gedaan is.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("1. Wat is de volgorde om een Scrum-proces te maken?");
            System.out.println("a) Epics > Userstories > Taken");
            System.out.println("b) Epics > Taken > Userstories");
            System.out.println("c) Userstories > Epics > Taken");
        } else if (huidigeVraag == 1) {
            System.out.println("2. Welke borden gebruik je in het Scrumboard?");
            System.out.println("a) Product Backlog > Sprint Backlog > Doing > Testing > Done");
            System.out.println("b) Product Backlog > Sprint Backlog > To Do > Doing > Testing > Done");
            System.out.println("c) Sprint Backlog > To Do > Doing > Testing > Done");
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler) {
        if (correct) {
            System.out.println("\n✅ Correct!");
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println();
        } else {
            System.out.println("\n❌ Fout, probeer opnieuw.");
            speler.voegMonsterToe("Scrum Verwarring");
            System.out.println("Monster 'Scrum Verwarring' verschijnt! Probeer het opnieuw.\n");

            System.out.println("Wil je een hint? Type 'ja' of 'nee'");
            String antwoord = scanner.nextLine().trim().toLowerCase();

            if(antwoord.equals("ja")){
                // 👇 Toon een hint
                hintContext.toonWillekeurigeHint(huidigeVraag);
            }
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
            } else if (antwoord.startsWith("gebruik ")) {
                String itemNaam = antwoord.substring(8).trim();
                speler.gebruikItem(itemNaam);
                System.out.println();
            } else if (antwoord.equals("naar andere kamer")) {
                System.out.println("Je verlaat deze kamer.\n");
                return;
            } else if (antwoord.matches("[a-d]")) {
                boolean antwoordCorrect = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
                verwerkResultaat(antwoordCorrect, speler);
            } else {
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'd', 'status', 'check', 'pak [item]', 'gebruik [item]', 'help' of 'naar andere kamer'.\n");
            }
        }

        setVoltooid();
        deur.setOpen(true);
        System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
        speler.voegVoltooideKamerToe(1);
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
        System.out.println("Gebruik 'check' om de items in de kamer te bekijken.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
    }
    public void bestrijdMonster(Speler speler) {
        System.out.println("Je bent in gevecht met het monster 'Sprint Confusie'!");
        System.out.println("Typ 'vecht' om het monster te bestrijden of 'vlucht' om terug te keren.");

        while (true) {
            String actie = scanner.nextLine().trim().toLowerCase();
            if (actie.equals("vecht")) {
                // Simpele kans op succes; je kan hier ook uitgebreidere logica maken
                boolean gewonnen = Math.random() > 0.5;
                if (gewonnen) {
                    System.out.println("Je hebt het monster verslagen!");
                    speler.verwijderMonster("Sprint Confusie");
                    break;
                } else {
                    System.out.println("Je hebt het monster niet verslagen, probeer het nogmaals!");
                }
            } else if (actie.equals("vlucht")) {
                System.out.println("Je bent gevlucht van het monster, probeer het later opnieuw.");
                break;
            } else {
                System.out.println("Typ 'vecht' of 'vlucht'.");
            }
        }
    }
}