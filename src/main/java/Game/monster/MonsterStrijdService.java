package Game.monster;

import Game.core.Speler;
import Game.monster.MonsterType;

import java.util.Scanner;
import Game.core.TextPrinter;

public class MonsterStrijdService {

    private static final Scanner scanner = new Scanner(System.in);

    public static void bestrijdMonster(Speler speler, MonsterType monster, String monsterNaam) {
        TextPrinter.print("❗ Monster '" + monsterNaam + "' verschijnt! Deze monster achtervolgt jou de hele spel tenzij je hem nu verslaat!");
        TextPrinter.print("Wil je de monster nu bestrijden? (ja/nee)");

        String keuze = scanner.nextLine().trim().toLowerCase();
        if (keuze.equals("nee")) {
            TextPrinter.print("De monster blijft je achtervolgen! Je kunt hem later bestrijden met het commando 'bestrijd monster'.");
            return;
        }

        int vragenTeBeantwoorden = 4;

        TextPrinter.print("Wil je een item gebruiken om het makkelijker te maken? (ja/nee)");
        if (scanner.nextLine().trim().equalsIgnoreCase("ja")) {
            TextPrinter.print("Welk item wil je gebruiken?");
            String itemNaam = scanner.nextLine().trim();
            boolean gebruikt = speler.gebruikItem(itemNaam);
            if (gebruikt) {
                if (itemNaam.equalsIgnoreCase("Scrum Zwaard")) {
                    vragenTeBeantwoorden = 0;
                    TextPrinter.print("⚔️ Het zwaard heeft het monster direct verslagen!");
                } else if (itemNaam.equalsIgnoreCase("Splitter")) {
                    vragenTeBeantwoorden = 2;
                    TextPrinter.print("🪓 Dankzij de Splitter hoef je maar 2 vragen te beantwoorden.");
                }
            }
        }

        for (int i = 0; i < vragenTeBeantwoorden; i++) {
            monster.verwerkOpdracht(i);
            System.out.print("Jouw antwoord: ");
            String antwoord = scanner.nextLine().trim().toLowerCase();
            if (!antwoord.equals(monster.getJuisteAntwoord(i).toLowerCase())) {
                speler.verliesLeven();
                System.out.println("❌ Fout antwoord! Monster " + monsterNaam + " heeft jou een klap gegeven... Je verliest een leven. Levens over: " + speler.getLevens());
                System.out.println();
                if (speler.getLevens() <= 0) {
                    TextPrinter.print("Game Over!");
                    return;
                }
            } else {
                TextPrinter.print("✅ Goed antwoord!");
            }
        }

        TextPrinter.print("🎉 Je hebt de monster verslagen!");
        speler.verwijderMonster(monsterNaam);
    }
}