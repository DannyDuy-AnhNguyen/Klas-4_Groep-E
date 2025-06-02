package Game.kamer;

import Game.antwoord.Antwoord;
import Game.item.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.HintContext;
import Game.hint.HelpHint;
import Game.hint.FunnyHint;
import Game.joker.HintJoker;
import Game.joker.Joker;
import Game.joker.KeyJoker;
import Game.joker.ToegestaandeKamers;
import Game.monster.VerliesVanFocus;

import java.util.List;

public class KamerDailyScrum extends Kamer {
    private final Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private final VerliesVanFocus verlies = new VerliesVanFocus();
    private Status status;


    public KamerDailyScrum(Antwoord antwoordStrategie) {
        super("Daily Scrum");
        this.antwoordStrategie = antwoordStrategie;
        deur.setOpen(false);
        toonHint();

    }

    @Override
    public void toonHint() {
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
            case 1 ->
                    System.out.println("De meeste sprints duren 1 tot 4 weken. Kort genoeg om snel te kunnen bijsturen.");
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
        } else {
            System.out.println("\n❌ Fout! Monster 'Verlies Van Focus' verschijnt!");
            speler.voegMonsterToe("VerliesVanFocus");
            bestrijdMonster(speler, verlies);
        }
    }

    @Override
    public void betreed(Speler speler) {
        if (!deur.isOpen()) {
            System.out.println("🚪 De deur is gesloten, je kunt deze kamer nog niet betreden.");
            deur.toonStatus();
            return;
        }

        System.out.println("🃏🔑In deze kamer kan je de 'KeyJoker' gebruiken!");
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
            } else if (antwoord.equals("joker")) {
                List<Joker> jokers = speler.getJokers();
                if (jokers.isEmpty()) {
                    System.out.println("❌ Je hebt geen jokers om te gebruiken.");
                    return;
                }

                System.out.println("🃏 Beschikbare jokers:");
                for (Joker joker : jokers) {
                    String status = joker.isUsed() ? " (gebruikt)" : "";
                    System.out.println("- " + joker.getNaam() + status);  // suggestie: voeg getNaam() toe in Joker-interface
                }

                System.out.println("Typ de naam van de joker die je wilt gebruiken (of typ 'annuleer'):");
                String gekozenJoker = scanner.nextLine().trim().toLowerCase();

                if (gekozenJoker.equals("annuleer")) {
                    System.out.println("❌ Jokerkeuze geannuleerd.");
                    return;
                }

                boolean jokerGebruikt = false;

                for (Joker joker : jokers) {
                    if (joker.isUsed()) continue;

                    if (joker.getNaam().equalsIgnoreCase(gekozenJoker)) {

                        // 💡 Check of de joker optioneel kamerspecificatie ondersteunt
                        if (joker instanceof ToegestaandeKamers kamerBeperkingen) {
                            if (!kamerBeperkingen.canBeUsedIn(this)) {
                                System.out.println("❌ Deze joker werkt niet in deze kamer.");
                                break;
                            }
                        }

                        System.out.println("Joker wordt gebruikt!: " + joker.getNaam());

                        if (joker.getNaam().equalsIgnoreCase("hint")) {
                            hintContext.toonWillekeurigeHint(huidigeVraag);
                        }

                        joker.useIn(this, speler);

                        // ✅ Na gebruik: verwijderen als hij gemarkeerd is als gebruikt
                        if (joker.isUsed()) {
                            speler.getJokers().remove(joker);
                        }
                        jokerGebruikt = true;
                        break;
                    }
                }


                if (!jokerGebruikt) {
                    System.out.println("❌ Geen geldige joker gevonden of reeds gebruikt.");
                }
                System.out.println();
            } else if (antwoord.startsWith("gebruik ")) {
                String itemNaam = antwoord.substring(8).trim();
                speler.gebruikItem(itemNaam);
                System.out.println();
            }
            else if (antwoord.equals("naar andere kamer")) {
                speler.setJokerGekozen(false);
                System.out.println("Je verlaat deze kamer.\n");
                return;
            } else if (antwoord.matches("[a-d]")) {
                boolean antwoordCorrect = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
                verwerkResultaat(antwoordCorrect, speler);
            } else {
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'd', 'status', 'check', 'pak [item]', 'gebruik [item]', 'help', 'joker' of 'naar andere kamer'.\n");
            }
        }

        if (!isVoltooid()) {
            setVoltooid();
            deur.setOpen(true);
            speler.setJokerGekozen(false);
            System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
            speler.voegSleutelToe();
            speler.voegVoltooideKamerToe(1);
        } else {
            System.out.println("✅ Kamer was al voltooid. Geen extra beloning.");
        }

    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return false;
    }

    @Override
    public void toonHelp() {
        System.out.println();
        System.out.println("Typ het letterantwoord: a, b, c of d");
        System.out.println("Gebruik 'status' om je huidige status te zien.");
        System.out.println("Gebruik 'check' om items in deze kamer te bekijken.");
        System.out.println("Gebruik 'help' om deze hulp te zien.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
        System.out.println("Typ bestrijd monster op elk moment als je een monster hebt die je nog moet bestrijden");
        System.out.println("Gebruik 'pak [itemnaam/itemnummer]' om een item op te pakken als je de item wilt claimen");
        System.out.println("Gebruik 'gebruik [itemnaam/itemnummer]' om een item te gebruiken");
        System.out.println("Gebruik 'joker' om een joker te gebruiken");
        System.out.println();
    }

    public void reset() {
        huidigeVraag = 0;
        deur.setOpen(false);
    }

    public void bestrijdMonster(Speler speler, VerliesVanFocus monster) {
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
}