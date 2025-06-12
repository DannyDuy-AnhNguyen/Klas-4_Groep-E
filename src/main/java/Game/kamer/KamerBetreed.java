package Game.kamer;

import Game.core.Speler;
import Game.database.DatabaseVoortgang;
import Game.joker.HintJokerInterface;
import Game.joker.Joker;
import Game.joker.KeyJokerInterface;
import Game.item.Item;
import Game.core.Status;
import Game.item.ItemBoek;
import Game.assistent.Assistent;

import java.util.List;
import java.util.Scanner;


public class KamerBetreed {

    private Scanner scanner = new Scanner(System.in);
    private int hintCounter = 0;

    //Ik heb hier 2 constructors toegevoegd voor de randwaardentest hoeveel hints die kan gebruiken
    // Default constructor
    public KamerBetreed() {
        this(new Scanner(System.in));
    }

    // Constructor met eigen scanner (voor testen) \\ hintJoker
    public KamerBetreed(Scanner scanner) {
        this.scanner = scanner;
    }

    public void betreedIntro(Kamer kamer) {
        System.out.println("\nJe bent nu in de kamer: " + kamer.getNaam());
        kamer.getDeur().toonStatus();
        System.out.println("📦 Items in deze kamer:");
        if (kamer.getItems().isEmpty()) {
            System.out.println("- Geen items beschikbaar.");
        } else {
            for (int i = 0; i < kamer.getItems().size(); i++) {
                System.out.println((i + 1) + ") " + kamer.getItems().get(i));
            }
        }
        System.out.println();
    }

    public void betreed(Kamer kamer, Speler speler) {
        if (!kamer.getDeur().isOpen()) {
            System.out.println("🚪 De deur is gesloten, je kunt deze kamer nog niet betreden.");
            kamer.getDeur().toonStatus();
            return;
        }

        if (!speler.isEersteKamerBetreden()) {
            ItemBoek info = new ItemBoek("boek");
            info.toonInfo(true);
            speler.markeerEersteKamerBetreden();
        }

        kamer.setStatus(new Status(speler));
        kamer.betreedIntro();

        int huidigeVraag = 0;

        while (huidigeVraag < 2) {
            kamer.verwerkOpdracht(huidigeVraag);

            String antwoord = scanner.nextLine().trim().toLowerCase();

            switch (antwoord) {
            // maakt gebruik van de commando's waar de methode opgeroepen wordt.
                case "help":
                    kamer.toonHelp();
                    System.out.println();
                    break;
                case "status":
                    kamer.getStatus().toonStatus();
                    System.out.println();
                    break;
                case "check":
                    toonItemsInKamer(kamer);
                    break;
                case "inventory":
                    speler.toonInventory();
                    System.out.println();
                    break;
                case "joker":
                    verwerkJoker(kamer, speler);
                    break;
                case "info":
                    ItemBoek.toonInfo(false);
                    System.out.println();
                    break;
                case "assistent":
                    Assistent assistent = Assistent.maakVoorKamer(kamer.getKamerID());
                    if (assistent != null) {
                        System.out.println();
                        assistent.activeer();
                    } else {
                        System.out.println();
                        System.out.println("❌ Er is geen assistent beschikbaar in deze kamer.");
                    }
                    System.out.println();
                    break;
                default:
                    if (antwoord.startsWith("pak ")) {
                        String itemInput = antwoord.substring(4).trim();
                        Item gekozenItem = null;

                        try {
                            int index = Integer.parseInt(itemInput) - 1;
                            List<Item> kamerItems = kamer.getItems();
                            if (index >= 0 && index < kamerItems.size()) {
                                gekozenItem = kamerItems.remove(index);
                            } else {
                                System.out.println("❌ Ongeldig itemnummer.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            gekozenItem = kamer.neemItem(itemInput);
                        }

                        if (gekozenItem != null) {
                            speler.voegItemToe(gekozenItem);
                            toonItemsInKamer(kamer); // herhaal check
                        } else if (!itemInput.matches("\\d+")) {
                            System.out.println("❌ Dat item is niet gevonden in deze kamer.");
                        }
                        System.out.println();
                    } else if (antwoord.startsWith("gebruik ")) {
                        String itemNaam = antwoord.substring(8).trim();
                        speler.gebruikItem(itemNaam);
                        System.out.println();
                    } else if (antwoord.equals("naar andere kamer")) {
                        speler.setJokerGekozen(false);
                        System.out.println("Je verlaat deze kamer.\n");
                        return;
                    } else if (antwoord.matches("[a-d]")) {
                        boolean antwoordCorrect = kamer.getAntwoordStrategie().verwerkAntwoord(antwoord, huidigeVraag);
                        kamer.verwerkResultaat(antwoordCorrect, speler);

                        if (antwoordCorrect) {
                            huidigeVraag++;
                        }
                    } else {
                        System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'status', 'check', 'inventory', 'pak [item]', 'gebruik [item]', 'help' of 'naar andere kamer'.\n");
                    }
            }
        }

        if (!kamer.isVoltooid()) {
            kamer.setVoltooid();
            kamer.getDeur().setOpen(true);
            speler.setJokerGekozen(false);
            System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
            speler.voegSleutelToe();
            speler.voegVoltooideKamerToe(kamer.getKamerID());
            DatabaseVoortgang.slaOp(speler, kamer.getKamerID());
            kamer.getStatus().toonStatus();
            System.out.println();
        } else {
            System.out.println("✅ Kamer was al voltooid. Geen extra beloning.");
        }
    }

    private void toonItemsInKamer(Kamer kamer) {
        List<Item> items = kamer.getItems();
        if (items.isEmpty()) {
            System.out.println("📦 Geen items in deze kamer.");
        } else {
            System.out.println("📦 Items in deze kamer:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i).getNaam());
            }
        }
        System.out.println();
    }

    //Voor de Randwaard test maak Danny de methode 'public'
    public void verwerkJoker(Kamer kamer, Speler speler) {
        List<Joker> jokers = speler.getJokers();
        if (jokers.isEmpty()) {
            System.out.println("❌ Je hebt geen jokers om te gebruiken.");
            return;
        }

        //Deze methode moet de joker's polymorfisme veranderen
        System.out.println("🃏 Beschikbare jokers:");
        for (Joker joker : jokers) {
            String status = joker.isUsed() ? " (gebruikt)" : "";
            System.out.println("- " + joker.getNaam() + status);
        }

        System.out.println("Typ de naam van de joker die je wilt gebruiken, type 'info' als je wilt weten hoe de jokers werkt (of typ 'annuleer':)");
        String gekozenJoker = scanner.nextLine().trim().toLowerCase();
        System.out.println();

        //Voor deze methode verwerkJoker heb ik een extra methode gemaakt zodat deze methode overzichtelijker is!
        if (gekozenJoker.equals("info")) {
            //Binnen deze methode krijg je meer info over hint en key
            provideJokerInfo(gekozenJoker);
            return;
        }


        if (gekozenJoker.equals("annuleer")) {
        System.out.println("❌ Jokerkeuze geannuleerd.");
        return;
        }

        boolean jokerGebruikt = false;

        for (Joker joker : jokers) {
            if (joker.isUsed()) continue;

            if (joker.getNaam().equalsIgnoreCase(gekozenJoker)) {

                if (joker instanceof KeyJokerInterface keyJoker) {
                    if (!keyJoker.canBeUsedIn(kamer)) {
                        System.out.println("❌ Deze joker werkt niet in deze kamer.");
                        break;
                    }
                    keyJoker.useInKey(kamer, speler);
                    jokerGebruikt = true;
                } else if(joker instanceof HintJokerInterface hintJoker){
                    if(validateMaxedUsedTotalHints(speler)){
                        hintJoker.useInHint(kamer);
                        jokerGebruikt = true;
                    } else {
                        System.out.println("❌ Je hebt al het maximum aantal hints gebruikt.");
                        return;
                    }
                }

                if (jokerGebruikt && joker.isUsed()) {
                    speler.getJokers().remove(joker);
                }
                break;
            }
        }

        if (!jokerGebruikt) {
            System.out.println("❌ Geen geldige joker gevonden of reeds gebruikt.");
        }
        System.out.println();
    }

    // Deze methode zorgt ervoor dat de gebruiker maximaal aantal hints kunt gebruiken.
    private boolean validateMaxedUsedTotalHints(Speler speler){
        return speler.gebruikHint();
    }

    private void provideJokerInfo(String gekozenJoker){
        System.out.println("ℹ️Bij welke joker wil je meer informatie weten? type dan 'hint' of 'key'. Wil je geen info, dan kun je iets willekeurig intypen.");
        gekozenJoker = scanner.nextLine().trim().toLowerCase();
        if(gekozenJoker.equals("hint")){
            System.out.println("📘Hint kun je in elke kamer gebruiken. \nElke speler kan maximaal 4 keer hints gebruiken. \nHints zijn kun je krijgen als je een kamer betreed. \nHints kun je alleen gebruiken bij de normale vragen.\nNIET BIJ MONSTER VRAGEN!!!\n");
        } else if(gekozenJoker.equals("key")){
            System.out.println("🗝️Key kun je alleen in de kamer 'Sprint Review' en 'Daily Scrum' krijgen en gebruiken.\nDe key zorgt ervoor dat je een extra sleutel kunt gebruiken waarmee je binnen andere kamers kunt betreden.\nOm dat te kunnen doen, type de commando 'naar andere kamer' en dan 'ga naar kamer [nummer/naam]'\n");
        }

    }

    public void toonHelp() {
        System.out.println();
        System.out.println("Typ het letterantwoord: a, b, c of d");
        System.out.println("Gebruik 'status' om je huidige status te zien.");
        System.out.println("Gebruik 'check' om items in deze kamer te bekijken.");
        System.out.println("Gebruik 'help' om deze hulp te zien.");
        System.out.println("Gebruik 'assistent' als je algemene hulp nodig hebt bij een kamer (note: je kan niet in elke kamer deze commando gebruiken!) ");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
        System.out.println("Typ bestrijd monster op elk moment als je een monster hebt die je nog moet bestrijden");
        System.out.println("Gebruik 'pak [itemnaam/itemnummer]' om een item op te pakken als je de item wilt claimen");
        System.out.println("Gebruik 'gebruik [itemnaam/itemnummer]' om een item te gebruiken");
        System.out.println("Gebruik 'joker' om een joker te gebruiken");
        System.out.println();
    }


    //Monster type test
    public String monsterType(int kamerID){
        switch (kamerID){
            case 1 -> {
                return "Misverstand";
            }
            case 2 -> {
                return "Sprint Confusie";
            }
            case 3 -> {
                return "VerliesVanFocus";
            }
            case 4 -> {
                return "Blame Game";
            }
            case 5 -> {
                return "Scrum Verwarring";
            }
            default -> {
                return "Speler verliest leven";
            }
        }
    }

    //Verwerk resultaat van elke kamer
    public void verwerkResultaat(boolean correct, Speler speler, Kamer kamer){
        if (correct) {
            System.out.println("\n✅ Correct!");
            speler.verhoogScore(10);

            int huidigeVraag = kamer.getHuidigeVraag(); // eerst ophalen
            kamer.verwerkFeedback(huidigeVraag);
            kamer.verhoogHuidigeVraag();
            System.out.println();
        } else {

            // ❗️Geen monsters in de finale kamer
            if (kamer.getKamerID() != 6) {
                System.out.println("\n❌ Fout, probeer opnieuw.");
                speler.voegMonsterToe(monsterType(kamer.getKamerID()));
                System.out.println("Monster '" + monsterType(kamer.getKamerID()) + "' verschijnt! Probeer het opnieuw.\n");
                kamer.bestrijdMonster(speler);
            } else {
                System.out.println("Deur blijft gesloten, maar er verschijnt geen monster in de finale kamer.\n");
                speler.verliesLeven();
            }
        }
    }
}