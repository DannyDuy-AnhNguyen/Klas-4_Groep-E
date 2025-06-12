package Game.core;

import Game.database.DatabaseVoortgang;
import Game.item.Item;
import Game.kamer.Kamer;

import java.util.List;
import java.util.Scanner;
import Game.core.GeluidSpeler;
import Game.core.TextPrinter;

import static Game.core.ConsoleKleuren.*;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    public Speler leesSpeler() {
        regenboogAnimatie("Welkom bij de Scrum Escape Game!", 20, 100);
        TextPrinter.print("Wat is je naam? ");
        String naam = scanner.nextLine().trim();

        Speler speler;

        if (DatabaseVoortgang.spelerBestaat(naam)) {
            TextPrinter.print("🧠 Speler '" + naam + "' bestaat al. Wil je doorgaan met je voortgang? (ja/nee): ");
            String keuze = scanner.nextLine().trim().toLowerCase();

            if (keuze.equals("ja")) {
                speler = DatabaseVoortgang.laadtSpeler(naam);
                if (speler != null) {
                    TextPrinter.print("✅ Voortgang geladen.");
                } else {
                    TextPrinter.print("⚠️ Fout bij laden. Nieuwe speler gestart.");
                    speler = new Speler();
                    speler.setNaam(naam);
                }
            } else {
                TextPrinter.print("🔄 Nieuw spel gestart. Oude data wordt overschreven bij opslaan.");
                speler = new Speler();
                speler.setNaam(naam);
            }
        } else {
            TextPrinter.print("🆕 Nieuwe speler wordt aangemaakt.");
            speler = new Speler();
            speler.setNaam(naam);
        }

        return speler;
    }

    public String leesInvoer() {
        System.out.print("> ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void printCommandoUitleg(String naam) {
        TextPrinter.print("Welkom, " + naam + "! Deze commando's kan je op elk moment gebruiken:");
        TextPrinter.print("'status', 'help', 'ga naar kamer X', 'check', 'pak [item]', 'gebruik [item]' of 'stop'.");
        System.out.println();
    }

    public void printHelp() {
        System.out.println();
        TextPrinter.print("🆘 Help:");
        TextPrinter.print("'status' - Bekijk je status.");
        TextPrinter.print("'help' - Toon deze hulptekst.");
        TextPrinter.print("'ga naar kamer X' - Ga naar een kamer.");
        TextPrinter.print("'check' - Bekijk items in de kamer.");
        TextPrinter.print("'pak [item]' - Pak een item uit de kamer.");
        TextPrinter.print("'gebruik [item]' - Gebruik een item uit je inventory.");
        TextPrinter.print("'stop' - Stop het spel.");
        System.out.println();
    }

    public void printKamerOpties(List<Kamer> kamers) {
        TextPrinter.print("📍 Beschikbare kamers:");
        for (int i = 0; i < kamers.size(); i++) {
            if (!kamers.get(i).isVoltooid()) {
                System.out.println((i + 1) + ". " + kamers.get(i).getNaam());
            }
        }
    }

    public void printItems(List<Item> items) {
        if (items.isEmpty()) {
            TextPrinter.print("📦 Geen items in deze kamer.");
        } else {
            TextPrinter.print("📦 Items in deze kamer:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i));
            }
        }
    }

    public void printOnbekendCommando() {
        TextPrinter.print("❌ Onbekend commando. Gebruik: ga naar kamer X");
    }

    public void printAfscheid() {
        TextPrinter.print("Tot ziens!");
    }

    public void printKamerVoltooid() {
        TextPrinter.print("✅ Deze kamer is voltooid!");
    }

    public void printGefeliciteerdArt() {
        System.out.println("""
                𝕲𝖊𝖋𝖊𝖑𝖎𝖈𝖎𝖙𝖊𝖊𝖗𝖉! 𝖏𝖊 𝖍𝖊𝖇𝖙 𝖍𝖊𝖙 𝖘𝖕𝖊𝖑 𝖛𝖔𝖑𝖙𝖔𝖔𝖎𝖉!
                𝕮𝖔𝖓𝖌𝖗𝖆𝖙𝖘! 𝕿𝖎𝖒𝖊 𝖙𝖔 𝖈𝖊𝖑𝖊𝖇𝖗𝖆𝖙𝖊 🎉

                𝕯𝖎𝖙 𝖎𝖘 𝖏𝖊 𝖈𝖗𝖔𝖜𝖓 𝖔𝖋 𝖛𝖎𝖈𝖙𝖔𝖗𝖞!
                𝕬𝖑𝖑𝖊 𝖕𝖗𝖔𝖌𝖗𝖆𝖒𝖒𝖆 𝖎𝖘 𝖉𝖔𝖓𝖊, 𝖈𝖔𝖉𝖊𝖗.

                　　　　＿＿
                　　　🌸＞　　フ   I don't want likes I want ham sandwich
                　　　| 　_　 _ l        (edit: Got ham sandwich)
                　　　／` ミ_wノ
                　　 /　　　 　 |
                　　 /　 ヽ　　 ﾉ
                　  │　　|　|　|
                　／￣|　　 |　|　|
                　| (￣ヽ＿_ヽ_)__)
                　＼二つ
                """);

        regenboogAnimatie("""
                𝕲𝖊𝖋𝖊𝖑𝖎𝖈𝖎𝖙𝖊𝖊𝖗𝖉! 𝖏𝖊 𝖍𝖊𝖇𝖙 𝖍𝖊𝖙 𝖘𝖕𝖊𝖑 𝖛𝖔𝖑𝖙𝖔𝖔𝖎𝖉!
                𝕮𝖔𝖓𝖌𝖗𝖆𝖙𝖘! 𝕿𝖎𝖒𝖊 𝖙𝖔 𝖈𝖊𝖑𝖊𝖇𝖗𝖆𝖙𝖊 🎉

                𝕯𝖎𝖙 𝖎𝖘 𝖏𝖊 𝖈𝖗𝖔𝖜𝖓 𝖔𝖋 𝖛𝖎𝖈𝖙𝖔𝖗𝖞!
                𝕬𝖑𝖑𝖊 𝖕𝖗𝖔𝖌𝖗𝖆𝖒𝖒𝖆 𝖎𝖘 𝖉𝖔𝖓𝖊, 𝖈𝖔𝖉𝖊𝖗.

                　　　　＿＿
                　　　🌸＞　　フ   I don't want likes I want ham sandwich
                　　　| 　_　 _ l        (edit: Got ham sandwich)
                　　　／` ミ_wノ
                　　 /　　　 　 |
                　　 /　 ヽ　　 ﾉ
                　  │　　|　|　|
                　／￣|　　 |　|　|
                　| (￣ヽ＿_ヽ_)__)
                　＼二つ
                """, 20, 200);
    }
}
