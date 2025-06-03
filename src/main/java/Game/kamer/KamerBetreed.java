package Game.kamer;

import Game.core.Speler;
import Game.core.Status;
import Game.item.Item;
import Game.joker.Joker;
import Game.joker.ToegestaandeKamers;
import Game.item.ItemBoek;

import java.util.List;
import java.util.Scanner;

public class KamerBetreed {

    private final Scanner scanner = new Scanner(System.in);

    private final Kamer kamer;

    public KamerBetreed(Kamer kamer) {
        this.kamer = kamer;
    }

    public void betreed(Speler speler) {
        if (!kamer.getDeur().isOpen()) {
            System.out.println("🚪 De deur is gesloten, je kunt deze kamer nog niet betreden.");
            kamer.getDeur().toonStatus();
            return;
        }

        if (!speler.isEersteKamerBetreden()) {
            ItemBoek info = new ItemBoek();
            info.toonInfo(true);
            speler.markeerEersteKamerBetreden();
        }

        kamer.setStatus(new Status(speler));
        kamer.betreedIntro();

        while (kamer.getHuidigeVraag() < 2) {
            kamer.verwerkOpdracht(kamer.getHuidigeVraag());

            String antwoord = scanner.nextLine().trim().toLowerCase();

            // Het commandogedeelte, vergelijkbaar met jouw code
            if (antwoord.equals("help")) {
                kamer.toonHelp();
                System.out.println();
            } else if (antwoord.equals("status")) {
                kamer.getStatus().update(speler);
                System.out.println();
            } else if (antwoord.equals("check")) {
                List<Item> items = kamer.getItems();
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
                }

                System.out.println("🃏 Beschikbare jokers:");
                for (Joker joker : jokers) {
                    String status = joker.isUsed() ? " (gebruikt)" : "";
                    System.out.println("- " + joker.getNaam() + status);
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
                        if (joker instanceof ToegestaandeKamers kamerBeperkingen) {
                            if (!kamerBeperkingen.canBeUsedIn(kamer)) {
                                System.out.println("❌ Deze joker werkt niet in deze kamer.");
                                break;
                            }
                        }

                        System.out.println("Joker wordt gebruikt!: " + joker.getNaam());

                        if (joker.getNaam().equalsIgnoreCase("hint")) {
                            kamer.getHintContext().toonWillekeurigeHint(kamer.getHuidigeVraag());
                        }

                        joker.useIn(kamer, speler);

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
            } else if (antwoord.equals("info")) {
                ItemBoek.toonInfo(false);
                System.out.println();
            } else if (antwoord.startsWith("pak ")) {
                String itemInput = antwoord.substring(4).trim();
                Item gekozenItem = null;

                try {
                    int index = Integer.parseInt(itemInput) - 1;
                    List<Item> items = kamer.getItems();
                    if (index >= 0 && index < items.size()) {
                        gekozenItem = items.remove(index);
                    } else {
                        System.out.println("❌ Ongeldig itemnummer.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    gekozenItem = kamer.neemItem(itemInput);
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
            } else if (antwoord.matches("[a-c]")) {
                boolean antwoordCorrect = kamer.getAntwoordStrategie().verwerkAntwoord(antwoord, kamer.getHuidigeVraag());
                kamer.verwerkResultaat(antwoordCorrect, speler);
            } else {
                System.out.println("Ongeldige invoer. Typ 'a', 'b', 'c', 'status', 'check', 'pak [item]', 'gebruik [item]', 'help', 'joker', 'info' of 'naar andere kamer'.\n");
            }
        }

        if (!kamer.isVoltooid()) {
            kamer.setVoltooid();
            kamer.getDeur().setOpen(true);
            speler.setJokerGekozen(false);
            System.out.println("🎉 Je hebt alle vragen juist beantwoord! De deur gaat open.");
            speler.voegSleutelToe();
            speler.voegVoltooideKamerToe(kamer.getKamerID());
        } else {
            System.out.println("✅ Kamer was al voltooid. Geen extra beloning.");
        }
    }
}