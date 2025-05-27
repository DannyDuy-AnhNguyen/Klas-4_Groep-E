package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.SprintConfusie;

import java.util.Scanner;

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
        System.out.println("Gebruik 'check' om items in deze kamer te bekijken.");
        System.out.println("Gebruik 'naar andere kamer' om deze kamer te verlaten.");
    }

    public void bestrijdMonster(Speler speler, SprintConfusie monster) {
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
