package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.Misverstand;

import java.util.Scanner;

public class KamerPlanning extends Kamer {
    private final Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private final Misverstand misverstand = new Misverstand();
    private final Scanner scanner = new Scanner(System.in);

    public KamerPlanning(Antwoord antwoordStrategie) {
        super("Sprint Planning");
        this.antwoordStrategie = antwoordStrategie;
        deur.setOpen(false);
        toonHint();
    }

    @Override
    public void toonHint() {
        hintContext.voegHintToe(0, new HelpHint("Bij de Sprint Planning doet iedereen mee."));
        hintContext.voegHintToe(0, new FunnyHint("Sprint Planning is het plannen met iedereen om .. een dorp aan te vallen."));
        hintContext.voegHintToe(1, new HelpHint("In de sprint planning wordt gekeken wat het doel van de aankomende sprint is."));
        hintContext.voegHintToe(1, new FunnyHint("Om een doelpunt te maken moet je sprinten naar het doel om een doelpunt te scoren."));
    }

    @Override
    public void betreedIntro() {
        System.out.println("\nJe bent nu in de kamer: " + naam);
        deur.toonStatus();
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
    public void verwerkFeedback(int vraag) {
        if (vraag == 0) {
            System.out.println("Bij de sprintplanning nemen alle betrokkenen deel aan wat er gepland gaat worden.");
        } else if (vraag == 1) {
            System.out.println("Tijdens de sprintplanning wordt vastgesteld wat het doel van de sprint is en welke backlog-items worden opgepakt.");
            System.out.println("Het team bepaalt ook de moeilijkheidsgraad van user stories met behulp van Sprint Poker.");
        }
    }

    @Override
    public void verwerkOpdracht(int vraag) {
        if (vraag == 0) {
            System.out.println("Vraag 1: Wie neemt deel aan de Sprint Planning?");
            System.out.println("a) Alleen de Scrum Master");
            System.out.println("b) Product Owner en Scrum Master");
            System.out.println("c) Product Owner, Scum Master en het hele Development Team");
            System.out.println("d) Product Owner, Scrum Master en het hele Development Team");
        } else if (vraag == 1) {
            System.out.println("Vraag 2: Wat wordt er tijdens de Sprint Planning vastgesteld?");
            System.out.println("a) Welke teamleden vakantie hebben");
            System.out.println("b) Wat het doel van de sprint is en welke backlog-items worden opgepakt");
            System.out.println("c) Hoe de vorige sprint geëvalueerd is");
            System.out.println("d) Wat de vastgestelde items van de backlog zijn als de product owner tevreden is met het product");
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
            System.out.println("\n❌ Fout! Monster 'Misverstand' verschijnt!");
            speler.voegMonsterToe("Misverstand");
            bestrijdMonster(speler, misverstand);
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
            } else if (antwoord.equals("status")) {
                status.update(speler);
            } else if (antwoord.equals("check")) {
                toonItems();
            } else if (antwoord.equals("naar andere kamer")) {
                System.out.println("Je verlaat deze kamer.\n");
                return;
            } else {
                if (antwoord.startsWith("pak ")) {
                    verwerkPak(antwoord.substring(4).trim(), speler);
                } else if (antwoord.startsWith("gebruik ")) {
                    speler.gebruikItem(antwoord.substring(8).trim());
                } else if (antwoord.matches("[a-d]")) {
                    boolean correct = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
                    verwerkResultaat(correct, speler);
                } else {
                    System.out.println("❌ Ongeldige invoer. Probeer opnieuw.");
                }
            }
        }

        setVoltooid();
        deur.setOpen(true);
        speler.voegVoltooideKamerToe(1);
        System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
    }

    private void toonItems() {
        if (items.isEmpty()) {
            System.out.println("📦 Geen items in deze kamer.");
        } else {
            System.out.println("📦 Items in deze kamer:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i));
            }
        }
    }

    private void verwerkPak(String input, Speler speler) {
        Item gekozenItem = null;

        try {
            int index = Integer.parseInt(input) - 1;
            if (index >= 0 && index < items.size()) {
                gekozenItem = items.remove(index);
            } else {
                System.out.println("❌ Ongeldig itemnummer.");
                return;
            }
        } catch (NumberFormatException e) {
            gekozenItem = neemItem(input);
        }

        if (gekozenItem != null) {
            speler.voegItemToe(gekozenItem);
        } else if (!input.matches("\\d+")) {
            System.out.println("❌ Dat item is niet gevonden in deze kamer.");
        }
    }

    public void bestrijdMonster(Speler speler, Misverstand monster) {
        System.out.println("❗ Monster 'Misverstand' verschijnt! Deze monster achtervolgt jou de hele spel tenzij je hem nu verslaat!");
        System.out.println("Wil je de monster nu bestrijden? (ja/nee)");

        String keuze = scanner.nextLine().trim().toLowerCase();
        if (keuze.equals("nee")) {
            System.out.println("De monster blijft je achtervolgen! Je kunt hem later bestrijden met het commando 'bestrijd monster'.");
            return;
        }

        int vragenTeBeantwoorden = 4;

        System.out.println("Wil je een item gebruiken om het makkelijker te maken? (ja/nee)");
        if (scanner.nextLine().trim().equalsIgnoreCase("ja")) {
            System.out.println("Welk item wil je gebruiken?");
            String itemNaam = scanner.nextLine().trim();
            boolean gebruikt = speler.gebruikItem(itemNaam);
            if (gebruikt) {
                if (itemNaam.equalsIgnoreCase("Scrum Zwaard")) {
                    vragenTeBeantwoorden = 0;
                    System.out.println("⚔️ Het zwaard heeft het monster direct verslagen!");
                } else if (itemNaam.equalsIgnoreCase("Splitter")) {
                    vragenTeBeantwoorden = 2;
                    System.out.println("🪓 Dankzij de Splitter hoef je maar 2 vragen te beantwoorden.");
                }
            }
        }

        for (int i = 0; i < vragenTeBeantwoorden; i++) {
            monster.verwerkOpdracht(i);
            System.out.print("Jouw antwoord: ");
            String antwoord = scanner.nextLine().trim().toLowerCase();
            if (!antwoord.equals(monster.getJuisteAntwoord(i).toLowerCase())) {
                speler.verliesLeven();
                System.out.println("Fout antwoord! Je verliest een leven. Levens over: " + speler.getLevens());
                if (speler.getLevens() <= 0) {
                    System.out.println("Game Over!");
                    return;
                }
            } else {
                System.out.println("✅ Goed antwoord!");
            }
        }

        System.out.println("🎉 Je hebt de monster verslagen!");
        speler.verwijderMonster("Misverstand");
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
        System.out.println("Gebruik 'pak [item]' om iets te pakken.");
        System.out.println("Gebruik 'gebruik [item]' om iets te gebruiken.");
        System.out.println("Gebruik 'help' voor deze uitleg.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
    }
}
