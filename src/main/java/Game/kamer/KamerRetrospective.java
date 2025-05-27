package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;

import java.util.Scanner;

public class KamerRetrospective extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private Status status;
    private final HintContext hintContext = new HintContext();
    private boolean introGetoond = false;
    private Scanner scanner;

    public KamerRetrospective(Antwoord antwoordStrategie) {
        super("Sprint Retrospective");
        this.antwoordStrategie = antwoordStrategie;
        this.scanner = new Scanner(System.in);
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
            System.out.println("RetroSpective zorgt ervoor dat de teamleden weten wat er goed gaat, beter kan en hoe ze het kunnen verbeteren.");
        } else if (huidigeVraag == 1) {
            System.out.println("Om te weten hoe de samenwerking daadwerkelijk gaat, is het verstandig om daar 1 tot 2 weken te geven of het beste na elke sprint.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Wat is het hoofddoel van de Sprint Retrospective?");
            System.out.println("a) De resultaten van het product demonstreren aan de klant.");
            System.out.println("b) De product backlog aanpassen.");
            System.out.println("c) Terugkijken op het proces en verbeteren waar mogelijk is.");
        } else if (huidigeVraag == 1) {
            System.out.println("Wanneer vindt de Sprint Retrospective plaats?");
            System.out.println("a) Aan het begin van de sprint");
            System.out.println("b) Direct na de Sprint Review, aan het einde van de sprint");
            System.out.println("c) Halverwege de Sprint");
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler) {
        if (correct) {
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("\n✅ Correct! Je krijgt 10 punten.\n");
        } else {
            speler.voegMonsterToe("Blame Game");
            System.out.println("\n❌ Fout! Monster 'Blame Game' verschijnt! Probeer het opnieuw.\n");

            System.out.println("Wil je een hint? Type 'ja' of 'nee'");
            String antwoord = scanner.nextLine().trim().toLowerCase();

            if(antwoord.equals("ja")){
                // 👇 Toon een hint
                hintContext.toonWillekeurigeHint(huidigeVraag);
            }

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
        System.out.println("Typ 'a', 'b' of 'c' om een antwoord te kiezen.");
        System.out.println("Typ 'status' om je huidige status te zien.");
        System.out.println("Typ 'check' om items in deze kamer te bekijken.");
        System.out.println("Typ 'naar andere kamer' om deze kamer te verlaten.");
        System.out.println("Typ 'help' om deze hulp te tonen.");
    }
}
