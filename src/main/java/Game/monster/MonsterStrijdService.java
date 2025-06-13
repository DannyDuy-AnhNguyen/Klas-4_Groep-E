package Game.monster;

import Game.core.Speler;
import Game.monster.MonsterType;

import java.util.Scanner;

//Deze klasse wordt uitgevoerd zodra je bij de normale kamer zoals 'Daily Scrum' een fout antwoord meegegeven hebt.
public class MonsterStrijdService {

    private static final Scanner scanner = new Scanner(System.in);

    public static void bestrijdMonster(Speler speler, MonsterType monster, String monsterNaam) {
        System.out.println("❗ Monster '" + monsterNaam + "' verschijnt! Deze monster achtervolgt jou de hele spel tenzij je hem nu verslaat!");
        System.out.println("Wil je de monster nu bestrijden? (ja/nee)");

        String keuze = scanner.nextLine().trim().toLowerCase();
        if (keuze.equals("nee")) {
            System.out.println("De monster blijft je achtervolgen! Je kunt hem later bestrijden met het commando 'bestrijd monster'.");
            return;
        }

        int vragenTeBeantwoorden = 4;

        System.out.println("Wil je een item gebruiken om het makkelijker te maken? (ja/nee)");
        String gebruikItemKeuze = scanner.nextLine().trim().toLowerCase();

        if (gebruikItemKeuze.equals("ja")) {
            while (true) {
                System.out.println("Welk item wil je gebruiken? (Typ 'stop' om geen item te gebruiken)");
                String itemNaam = scanner.nextLine().trim();

                if (itemNaam.equalsIgnoreCase("stop")) {
                    System.out.println("Je hebt gekozen om geen item te gebruiken.");
                    break;
                }

                boolean gebruikt = speler.gebruikItem(itemNaam);
                if (gebruikt) {
                    if (itemNaam.equalsIgnoreCase("Scrum Zwaard")) {
                        vragenTeBeantwoorden = 0;
                        System.out.println("⚔️ Het zwaard heeft het monster direct verslagen!");
                    } else if (itemNaam.equalsIgnoreCase("Splitter")) {
                        vragenTeBeantwoorden = 2;
                        System.out.println("🪓 Dankzij de Splitter hoef je maar 2 vragen te beantwoorden.");
                    }
                    break; // geldige invoer, uit de loop
                } else {
                    System.out.println("Je hebt dit item niet in je inventory of het item bestaat niet. Probeer opnieuw.");
                }
            }
        }

        //Deze code controleert of je antwoord goed is of niet. ZO niet, dan verlies je een leven.
        //Elke speler heeft 3 levens.
        for (int i = 0; i < vragenTeBeantwoorden; i++) {
            monster.verwerkOpdracht(i);
            System.out.print("Jouw antwoord: ");
            String antwoord = scanner.nextLine().trim().toLowerCase();
            if (!antwoord.equals(monster.getJuisteAntwoord(i).toLowerCase())) {
                speler.verliesLeven();
                System.out.println("❌ Fout antwoord! Monster " + monsterNaam + " heeft jou een klap gegeven... Je verliest een leven. Levens over: " + speler.getLevens());
                System.out.println();
                if (speler.getLevens() <= 0) {
                    System.out.println("Game Over!");
                    return;
                }
            } else {
                System.out.println("✅ Goed antwoord!");
            }
        }

        System.out.println("🎉 Je hebt de monster verslagen!");
        speler.verwijderMonster(monsterNaam);
    }
}
