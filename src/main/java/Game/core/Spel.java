package Game.core;

import Game.kamer.Kamer;
import Game.kamer.KamerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Spel {
    private Speler speler;
    private List<Kamer> kamers;
    private Scanner scanner;
    private Status status;
    private KamerFactory kamerFactory;
    private Kamer huidigeKamer = null;

    public Spel() {
        this.scanner = new Scanner(System.in);
        this.speler = new Speler();
        this.kamers = new ArrayList<>();
        this.status = new Status(speler);
        this.kamerFactory = new KamerFactory();

        List<String> kamerKeys = kamerFactory.getKamerKeys();
        for (String key : kamerKeys) {
            Kamer kamer = kamerFactory.getKamer(key);
            kamer.getDeur().setOpen(false);
            kamers.add(kamer);
        }
    }

    public void start() {
        System.out.println("Welkom bij de Scrum Escape Game!");
        System.out.print("Wat is je naam? ");
        speler.setNaam(scanner.nextLine());

        System.out.println("Welkom, " + speler.getNaam() + "! Deze commando's kan je op elk moment gebruiken:");
        System.out.println("'status', 'help', 'ga naar kamer X', 'check', 'pak [item]', 'gebruik [item]' of 'stop'.");
        System.out.println();

        while (true) {
            if (huidigeKamer == null || huidigeKamer.isVoltooid()) {
                toonKamerOpties();
            }

            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("stop")) {
                System.out.println("Tot ziens!");
                return;
            } else if (input.equals("status")) {
                status.update(speler);
            } else if (input.equals("help")) {
                toonHelp();
            } else if (input.equals("check")) {
                verwerkCheck();
            } else if (input.startsWith("pak ")) {
                verwerkPak(input);
            } else if (input.startsWith("gebruik ")) {
                speler.gebruikItem(input.substring(8).trim());
            } else if (input.startsWith("ga naar kamer")) {
                verwerkKamerCommando(input); // => stelt ook huidigeKamer in
            } else if (huidigeKamer != null && !huidigeKamer.isVoltooid()) {
                // 👉 Stuur het antwoord door naar de kamer zelf
                huidigeKamer.verwerkAntwoord(input, speler);

                if (huidigeKamer.isVoltooid()) {
                    System.out.println("✅ Deze kamer is voltooid!");
                    controleerEnStartFinale();
                    huidigeKamer = null;
                }
            } else {
                System.out.println("❌ Onbekend commando. Gebruik: ga naar kamer X");
            }
        }

    }

    private void verwerkKamerCommando(String input) {
        try {
            String argument = input.substring("ga naar kamer".length()).trim();
            Kamer gekozenKamer = null;

            try {
                int nummer = Integer.parseInt(argument.trim()) - 1;
                if (nummer >= 0 && nummer < kamers.size()) {
                    gekozenKamer = kamers.get(nummer);
                }
            } catch (NumberFormatException e) {
                gekozenKamer = kamerFactory.getKamer(argument.replaceAll("\\s+", "").toLowerCase());
            }

            if (gekozenKamer == null) {
                System.out.println("Onbekende kamer: " + argument);
                return;
            }

            if (!gekozenKamer.getDeur().isOpen()) {
                gekozenKamer.getDeur().setOpen(true);
                System.out.println("🔓 De deur naar '" + gekozenKamer.getNaam() + "' is geopend.");
            }

            if (!gekozenKamer.isVoltooid()) {
//                huidigeKamer = gekozenKamer;
                gekozenKamer.betreed(speler);
                if (gekozenKamer.isVoltooid()) {
                    System.out.println("✅ Deze kamer is voltooid!");
                    controleerEnStartFinale();
                }
            } else {
                System.out.println("Deze kamer is al voltooid.");
            }
        } catch (Exception e) {
            System.out.println("❌ Ongeldige invoer. Gebruik: ga naar kamer X");
        }
    }

    private void controleerEnStartFinale() {
        if (alleNormaleKamersVoltooid() && !isFinaleKamerVoltooid()) {
            Kamer finaleKamer = kamerFactory.getKamer("Finale TIA Kamer – Waarom Scrum?");
            if (!kamers.contains(finaleKamer)) {
                finaleKamer.getDeur().setOpen(true);
                kamers.add(finaleKamer);
                System.out.println("🏁 Alle kamers voltooid! De finale kamer is nu beschikbaar.");
            }

            // Zet spelerpositie correct naar de nieuwe kamer
            speler.setPositie(kamers.indexOf(finaleKamer));
            System.out.println("➡️ Je gaat automatisch naar de finale kamer!");
            finaleKamer.betreed(speler);
            status.update(speler);

            if (finaleKamer.isVoltooid()) {
                printGefeliciteerdArt();
                System.exit(0);
            }
        }
    }

    private void verwerkCheck() {
        Kamer kamer = kamers.get(speler.getPositie());
        List<Item> kamerItems = kamer.getItems();
        if (kamerItems.isEmpty()) {
            System.out.println("📦 Geen items in deze kamer.");
        } else {
            System.out.println("📦 Items in deze kamer:");
            for (int i = 0; i < kamerItems.size(); i++) {
                System.out.println((i + 1) + ") " + kamerItems.get(i));
            }
        }
    }

    private void verwerkPak(String input) {
        String itemInput = input.substring(4).trim();
        Kamer kamer = kamers.get(speler.getPositie());
        List<Item> kamerItems = kamer.getItems();
        Item gekozenItem = null;

        try {
            int index = Integer.parseInt(itemInput) - 1;
            if (index >= 0 && index < kamerItems.size()) {
                gekozenItem = kamerItems.remove(index);
            } else {
                System.out.println("❌ Ongeldig itemnummer.");
                return;
            }
        } catch (NumberFormatException e) {
            gekozenItem = kamer.neemItem(itemInput);
        }

        if (gekozenItem != null) {
            speler.voegItemToe(gekozenItem);
        } else if (!itemInput.matches("\\d+")) {
            System.out.println("❌ Dat item is niet gevonden in deze kamer.");
        }
    }

    private boolean alleNormaleKamersVoltooid() {
        for (Kamer kamer : kamers) {
            if (!kamer.getNaam().toLowerCase().contains("finale") && !kamer.isVoltooid()) {
                return false;
            }
        }
        return true;
    }

    private boolean isFinaleKamerVoltooid() {
        for (Kamer kamer : kamers) {
            if (kamer.getNaam().toLowerCase().contains("finale") && kamer.isVoltooid()) {
                return true;
            }
        }
        return false;
    }

    private void toonKamerOpties() {
        System.out.println("📍 Beschikbare kamers:");
        for (int i = 0; i < kamers.size(); i++) {
            if (!kamers.get(i).isVoltooid()) {
                System.out.println((i + 1) + ". " + kamers.get(i).getNaam());
            }
        }
    }

    private void toonHelp() {
        System.out.println("🆘 Help:");
        System.out.println("'status' - Bekijk je status.");
        System.out.println("'help' - Toon deze hulptekst.");
        System.out.println("'ga naar kamer X' - Ga naar een kamer.");
        System.out.println("'check' - Bekijk items in de kamer.");
        System.out.println("'pak [item]' - Pak een item uit de kamer.");
        System.out.println("'gebruik [item]' - Gebruik een item uit je inventory.");
        System.out.println("'stop' - Stop het spel.");
    }

    private void printGefeliciteerdArt() {
        String art =
                "𝕲𝖊𝖋𝖊𝖑𝖎𝖈𝖎𝖙𝖊𝖊𝖗𝖉! 𝖏𝖊 𝖍𝖊𝖇𝖙 𝖍𝖊𝖙 𝖘𝖕𝖊𝖑 𝖛𝖔𝖑𝖙𝖔𝖔𝖎𝖉!\n" +
                        "  𝕮𝖔𝖓𝖌𝖗𝖆𝖙𝖘! 𝕿𝖎𝖒𝖊 𝖙𝖔 𝖈𝖊𝖑𝖊𝖇𝖗𝖆𝖙𝖊 🎉\n" +
                        "\n" +
                        "  𝕯𝖎𝖙 𝖎𝖘 𝖏𝖊 𝖈𝖗𝖔𝖜𝖓 𝖔𝖋 𝖛𝖎𝖈𝖙𝖔𝖗𝖞!\n" +
                        "  𝕬𝖑𝖑𝖊 𝖕𝖗𝖔𝖌𝖗𝖆𝖒𝖒𝖆 𝖎𝖘 𝖉𝖔𝖓𝖊, 𝖈𝖔𝖉𝖊𝖗.\n\n" +

                        "　　　　＿＿\n" +
                        "　　　🌸＞　　フ   I don't want likes I want ham sandwich\n" +
                        "　　　| 　_　 _ l        (edit: Got ham sandwich)\n" +
                        "　　　／` ミ_wノ\n" +
                        "　　 /　　　 　 |\n" +
                        "　　 /　 ヽ　　 ﾉ\n" +
                        "　  │　　|　|　|\n" +
                        "　／￣|　　 |　|　|\n" +
                        "　| (￣ヽ＿_ヽ_)__)\n" +
                        "　＼二つ\n";

        System.out.println(art);
        System.out.println("");
    }
}