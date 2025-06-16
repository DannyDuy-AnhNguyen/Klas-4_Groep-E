package Game.monster;

import Game.core.Speler;
import Game.monster.MonsterType;

import java.util.Scanner;
import Game.item.*;

public class MonsterStrijdService {

    private static final Scanner scanner = new Scanner(System.in);

    public static void bestrijdMonster(Speler speler, MonsterType monster, String monsterNaam) {
        System.out.println("Deze monster achtervolgt jou de hele spel tenzij je hem nu verslaat!");

        // Vraag om bevestiging
        String keuze;
        while (true) {
            System.out.println("Wil je de monster nu bestrijden? (ja/nee): ");
            keuze = scanner.nextLine().trim().toLowerCase();
            if (keuze.equals("ja") || keuze.equals("nee")) break;
            System.out.println("Ongeldige invoer. Typ 'ja' of 'nee'.");
        }

        if (keuze.equals("nee")) {
            System.out.println("De monster blijft je achtervolgen! Je kunt hem later bestrijden met het commando 'bestrijd monster'.");
            return;
        }

        int vragenTeBeantwoorden = 4;

        // Item gebruiken
        System.out.print("Wil je een item gebruiken om het makkelijker te maken? (ja/nee): ");
        String gebruikItemKeuze = scanner.nextLine().trim().toLowerCase();

        if (gebruikItemKeuze.equals("ja")) {
            speler.toonInventory();
            while (true) {
                System.out.print("Welk item wil je gebruiken? (Typ 'stop' om over te slaan): ");
                String itemNaam = scanner.nextLine().trim();

                if (itemNaam.equalsIgnoreCase("stop")) {
                    System.out.println("Je hebt gekozen om geen item te gebruiken.");
                    break;
                }

                Item item = speler.getInventory().stream()
                        .filter(i -> i.getNaam().equalsIgnoreCase(itemNaam))
                        .findFirst()
                        .orElse(null);

                if (item == null) {
                    System.out.println("❌ Dat item zit niet in je inventory. Probeer opnieuw.");
                    continue;
                }

                boolean gebruikt = false;

                // Direct verslaan
                if (item instanceof GebruiktVoorMonster wapen) {
                    wapen.gebruikTegenMonster();
                    vragenTeBeantwoorden = 0;
                    gebruikt = true;
                }

                // Veranderen van aantal vragen
                else if (item instanceof VerandertAantalVragen verminderaar) {
                    vragenTeBeantwoorden = verminderaar.pasAantalVragenAan(vragenTeBeantwoorden);
                    gebruikt = true;
                }

                if (gebruikt) {
                    speler.getInventory().remove(item);
                    speler.notifyObservers();
                    break;
                }

                System.out.println("Dit item kan hier niet gebruikt worden. Probeer een ander.");
            }
        }

        // Monsterbestrijding
        for (int i = 0; i < vragenTeBeantwoorden; i++) {
            boolean goedBeantwoord = false;

            while (!goedBeantwoord) {
                monster.verwerkOpdracht(i);
                String antwoord;

                while (true) {
                    System.out.print("");
                    antwoord = scanner.nextLine().trim().toLowerCase();
                    if (antwoord.matches("[abcd]")) break;
                    System.out.println("Ongeldige invoer. Typ alleen a, b, c of d.");
                }

                if (!antwoord.equals(monster.getJuisteAntwoord(i).toLowerCase())) {
                    speler.verliesLeven();
                    System.out.println("Fout antwoord! Monster " + monsterNaam + " heeft jou een klap gegeven... Levens over: " + speler.getLevens());

                    if (speler.getLevens() <= 0) {
                        System.out.println("💀 Game Over!");
                        return;
                    }
                } else {
                    System.out.println("Goed antwoord!");
                    goedBeantwoord = true;
                }
            }
        }

        System.out.println("🎉 Je hebt het monster verslagen!");
        speler.verwijderMonster(monsterNaam);
    }
}