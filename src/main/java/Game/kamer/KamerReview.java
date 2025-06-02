package Game.kamer;

import Game.antwoord.Antwoord;
import Game.item.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.joker.Joker;
import Game.joker.ToegestaandeKamers;
import Game.monster.BlameGame;
import Game.monster.SprintConfusie;

import java.util.List;

public class KamerReview extends Kamer {
    private Antwoord antwoordStrategie;
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private final SprintConfusie sprintConfusie = new SprintConfusie();

    public KamerReview(Antwoord antwoordStrategie) {
        super("Sprint Review");
        deur.setOpen(false);
        this.antwoordStrategie = antwoordStrategie;
        toonHint();
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Het begint altijd na de sprint"));
        hintContext.voegHintToe(0, new FunnyHint("Wordt je geinterviewed nadat je gesprint hebt voor de Olympische Spelen?"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Na de sprint wordt er teruggekeken wat je afgelopen sprint gekeken wat je met je team gedaan hebt"));
        hintContext.voegHintToe(1, new FunnyHint("Om goed te kunnen sprinten, moet je naar de feedback van Usain Bolt luisteren. Hij is een lightning bolt⚡"));

        // 🎯 Hints voor vraag 2
        hintContext.voegHintToe(2, new HelpHint("Wat is nuttig om bij een review te houden?"));
        hintContext.voegHintToe(2, new FunnyHint("Sprint review geeft jou ook speciale superkracht om 'Transparant' te zijn!"));
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
    public void verwerkResultaat(boolean correct, Speler speler) {
        if (correct) {
            speler.verhoogScore(10);
            verwerkFeedback(huidigeVraag);
            huidigeVraag++;
            System.out.println("Correct!\n");
        } else {
            speler.voegMonsterToe("Sprint Confusie");
            System.out.println("Monster 'Sprint Confusie' verschijnt! Probeer het opnieuw.\n");

            // Start de monster strijd
            bestrijdMonster(speler, sprintConfusie);
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

        setVoltooid();
        deur.setOpen(true);
        speler.setJokerGekozen(false);
        System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
        speler.voegVoltooideKamerToe(1);
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

    public void bestrijdMonster(Speler speler, SprintConfusie monster) {
        System.out.println("❗ Monster 'Sprint Confusie' verschijnt! Deze monster achtervolgt jou de hele spel tenzij je hem nu verslaat!");
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
        speler.verwijderMonster("Sprint Confusie");
    }
}
