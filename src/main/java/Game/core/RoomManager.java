package Game.core;

import Game.item.Item;
import Game.kamer.Kamer;
import Game.kamer.KamerFactory;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private final List<Kamer> kamers = new ArrayList<>();
    private final KamerFactory kamerFactory;
    private final ToegangsManager toegangsManager;
    private final InventoryManager inventoryManager;

    public RoomManager(KamerFactory kamerFactory, Speler speler) {
        this.kamerFactory = kamerFactory;
        this.toegangsManager = new ToegangsManager();
        this.inventoryManager = new InventoryManager(speler);

        for (String key : kamerFactory.getKamerKeys()) {
            Kamer kamer = kamerFactory.getKamer(key);
            kamer.getDeur().setOpen(false);
            kamers.add(kamer);
        }
    }

    public List<Kamer> getBeschikbareKamers() {
        return kamers;
    }

    public Kamer getKamerOpPositie(int positie) {
        return kamers.get(positie);
    }

    public Kamer verwerkKamerCommando(String input, Speler speler) {
        String prefix = "ga naar kamer";
        if (input.length() <= prefix.length()) {
            TextPrinter.print("Ongeldig commando, gebruik: 'ga naar kamer [nummer|naam]'");
            System.out.println();
            return null;
        }

        String argument = input.substring(prefix.length()).trim();
        Kamer gekozenKamer = parseerKamer(argument);

        if (gekozenKamer == null) {
            System.out.println("Onbekende kamer: " + argument);
            System.out.println();
            return null;
        }

        if (!toegangsManager.openDeurAlsMogelijk(speler, gekozenKamer)) {
            return null;
        }

        if (!speler.isJokerGekozen()) {
            gekozenKamer.initSpeler(speler);
            speler.setJokerGekozen(true);
        }

        return gekozenKamer;
    }

    private Kamer parseerKamer(String argument) {
        try {
            int nummer = Integer.parseInt(argument) - 1;
            if (nummer >= 0 && nummer < kamers.size()) {
                return kamers.get(nummer);
            }
        } catch (NumberFormatException e) {
            return kamerFactory.getKamer(argument.replaceAll("\\s+", "").toLowerCase());
        }
        return null;
    }

    public boolean alleNormaleKamersVoltooid() {
        return kamers.stream()
                .filter(k -> !k.getNaam().toLowerCase().contains("finale"))
                .allMatch(Kamer::isVoltooid);
    }

    public boolean isFinaleKamerVoltooid() {
        return kamers.stream()
                .filter(k -> k.getNaam().toLowerCase().contains("finale"))
                .anyMatch(Kamer::isVoltooid);
    }

    public Kamer activeerFinaleKamer(Speler speler) {
        Kamer finale = kamerFactory.getKamer("Finale TIA Kamer – Waarom Scrum?");
        System.out.println("Activeer finale kamer: " + finale.getNaam());
        System.out.println();

        finale.getDeur().setOpen(true);

        if (!kamers.contains(finale)) {
            kamers.add(finale);
            TextPrinter.print("Finale kamer toegevoegd aan kamers lijst.");
            System.out.println();
        }

        speler.setPositie(kamers.indexOf(finale));
        System.out.println("Speler positie naar finale kamer: " + speler.getPositie());
        System.out.println();

        return finale;
    }

    public void verwerkPak(String input, Speler speler) {
        String itemInput = input.substring(4).trim();
        Kamer kamer = getKamerOpPositie(speler.getPositie());
        List<Item> kamerItems = kamer.getItems();
        Item gekozenItem = null;

        try {
            int index = Integer.parseInt(itemInput) - 1;
            if (index >= 0 && index < kamerItems.size()) {
                gekozenItem = kamerItems.remove(index);
            } else {
                TextPrinter.print("❌ Ongeldig itemnummer.");
                System.out.println();
                return;
            }
        } catch (NumberFormatException e) {
            gekozenItem = kamer.neemItem(itemInput);
        }

        if (gekozenItem != null) {
            inventoryManager.voegItemToe(gekozenItem);
        } else if (!itemInput.matches("\\d+")) {
            TextPrinter.print("❌ Dat item is niet gevonden in deze kamer.");
            System.out.println();
        }
    }
}