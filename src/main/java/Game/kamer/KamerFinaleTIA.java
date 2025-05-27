package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;

import java.util.Scanner;

public class KamerFinaleTIA extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private Scanner scanner;

    public KamerFinaleTIA(Antwoord antwoordStrategie) {
        super("Finale TIA Kamer – Waarom Scrum?");
        this.antwoordStrategie = antwoordStrategie;
        this.scanner = new Scanner(System.in);
        toonHint();
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Het is iemand die niet in de scrum werkt"));
        hintContext.voegHintToe(0, new FunnyHint("Scrum is een ultieme methode om milieu niet te vervuilen"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Scrum is officieel ontstaan in 1995."));
        hintContext.voegHintToe(1, new FunnyHint("5 jaar geleden voordat het jaar 2000 is geboren."));

        // 🎯 Hints voor vraag 2
        hintContext.voegHintToe(2, new HelpHint("Deze vraag is grappig bedoeld, maar Scrum is serieus."));
        hintContext.voegHintToe(2, new FunnyHint("IT'S ME, MARIO WAHOOOOO!"));

        // 🎯 Hints voor vraag 3
        hintContext.voegHintToe(3, new HelpHint("Sprint 0 wordt vaak gebruikt voor voorbereiding en planning."));
        hintContext.voegHintToe(3, new FunnyHint("3, 2, 1, NUUUULLLLL!!!!"));
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
            System.out.println("Het is iemand die niet in de scrum werkt");
        } else if (huidigeVraag == 1) {
            System.out.println("Scrum is officieel ontstaan in 1995.");
        } else if (huidigeVraag == 2) {
            System.out.println("Deze vraag is grappig bedoeld, maar Scrum is serieus.");
        } else if (huidigeVraag == 3) {
            System.out.println("Sprint 0 wordt vaak gebruikt voor voorbereiding en planning.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag){
        if (huidigeVraag == 0) {
            System.out.println("1. Wat vind je van Scrum?");
            System.out.println("a) Uitstekend");
            System.out.println("b) Neutraal");
            System.out.println("c) Slecht");
        } else if (huidigeVraag == 1) {
            System.out.println("2. Uit welk jaar is Scrum ontstaan?");
            System.out.println("a) 1993");
            System.out.println("b) 1995");
            System.out.println("c) 2001");
            System.out.println("d) 2010");
        } else if (huidigeVraag == 2) {
            System.out.println("3. Is Scrum Scrumario?");
            System.out.println("a) Ja");
            System.out.println("b) Ja");
            System.out.println("c) Ja");
            System.out.println("d) Ja");
        } else if (huidigeVraag == 3) {
            System.out.println("4. Bij welke sprint hoort deze userstory?");
            System.out.println("(Typ je antwoord, bijvoorbeeld 'Sprint 0')");
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler){
        if (correct) {
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("Correct!\n");
        } else {
            System.out.println("Fout antwoord! De deur blijft gesloten en Monster 'Scrum Misverstanden' verschijnt!\n");

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
        boolean correct = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
        updateScore(correct, speler);
        return correct;
    }

    public boolean verwerkAntwoordOpenVraag(String antwoord) {
        return antwoord.matches("^(0|sprint 0|nul|sprintnul|sprint0)$");
    }

    @Override
    public void toonHelp() {
        System.out.println("Typ 'a', 'b', 'c' of 'd' om een antwoord te kiezen.");
        System.out.println("Typ 'status' om je huidige status te zien.");
        System.out.println("Typ 'check' om items in deze kamer te bekijken.");
        System.out.println("Typ 'naar andere kamer' om deze kamer te verlaten.");
        System.out.println("Typ 'help' om deze hulp te tonen.");
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
