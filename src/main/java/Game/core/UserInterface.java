package Game.core;

import Game.database.DatabaseVoortgang;
import Game.item.Item;
import Game.kamer.Kamer;

import java.util.List;
import java.util.Scanner;
import Game.core.GeluidSpeler;

import static Game.core.ConsoleKleuren.*;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    public Speler leesSpeler() {
        regenboogAnimatie("Welkom bij de Scrum Escape Game!", 20, 100);
        System.out.print("Wat is je naam? ");
        String naam = scanner.nextLine().trim();

        Speler speler;

        if (DatabaseVoortgang.spelerBestaat(naam)) {
            System.out.print("🧠 Speler '" + naam + "' bestaat al. Wil je doorgaan met je voortgang? (ja/nee): ");
            String keuze = scanner.nextLine().trim().toLowerCase();

            if (keuze.equals("ja")) {
                speler = DatabaseVoortgang.laadtSpeler(naam);
                if (speler != null) {
                    System.out.println("✅ Voortgang geladen.");
                } else {
                    System.out.println("⚠️ Fout bij laden. Nieuwe speler gestart.");
                    speler = new Speler();
                    speler.setNaam(naam);
                }
            } else {
                System.out.println("🔄 Nieuw spel gestart. Oude data wordt overschreven bij opslaan.");
                speler = new Speler();
                speler.setNaam(naam);
            }
        } else {
            System.out.println("🆕 Nieuwe speler wordt aangemaakt.");
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
        System.out.println("Welkom, " + naam + "! Deze commando's kan je op elk moment gebruiken:");
        System.out.println("'status', 'help', 'ga naar kamer X', 'check', 'pak [item]', 'gebruik [item]' of 'stop'.");
        System.out.println();
    }

    public void printHelp() {
        System.out.println();
        System.out.println("🆘 Help:");
        System.out.println("'status' - Bekijk je status.");
        System.out.println("'help' - Toon deze hulptekst.");
        System.out.println("'ga naar kamer X' - Ga naar een kamer.");
        System.out.println("'check' - Bekijk items in de kamer.");
        System.out.println("'pak [item]' - Pak een item uit de kamer.");
        System.out.println("'gebruik [item]' - Gebruik een item uit je inventory.");
        System.out.println("'stop' - Stop het spel.");
        System.out.println();
    }

    public void printKamerOpties(List<Kamer> kamers, Speler speler) {
        System.out.println("📍 Beschikbare kamers:");
        //Kijkt in de kamer lijst welke kamers beschikbaar zijn.
        for (Kamer kamer : kamers) {
            boolean voltooid = speler.getVoltooideKamers().contains(kamer.getKamerID());
            boolean deurOpen = kamer.getDeur().isOpen();

            //In de if zijn de deuren van de kamers gesloten en en de kamers zijn natuurlijk nog niet voltooid als je een nieuw spel begint.
            if (!deurOpen && !voltooid) {
                System.out.println(printBeschikbareKamers(kamer)); //Deze methode print de beschikbare kamers.
            }
            //In de else if is voor het geval als de speler binnen een kamer bijvoorbeeld 'Daily Scrum' zit
            // die de commando 'naar andere kamer' meegeeft zodat je nog de kamer kunt zien die je nog niet voltooid hebt.
            else if(deurOpen && !voltooid) {
                System.out.println(printBeschikbareKamers(kamer)); //Deze methode print de beschikbare kamers.
            }
        }
    }

    //Deze methode voorkomt de duplicatie code binnen de methode 'printKamerOpties'.
    private String printBeschikbareKamers(Kamer kamer){
        return kamer.getKamerID() + ". " + kamer.getNaam();
    }

    public void printItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("📦 Geen items in deze kamer.");
        } else {
            System.out.println("📦 Items in deze kamer:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ") " + items.get(i));
            }
        }
    }

    public void printOnbekendCommando() {
        System.out.println("❌ Onbekend commando. Gebruik: ga naar kamer X");
    }

    public void printAfscheid() {
        System.out.println("Tot ziens!");
    }

    public void printKamerVoltooid() {
        System.out.println("✅ Deze kamer is voltooid!");
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
    }
}