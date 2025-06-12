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
    private InventoryManager inventoryManager;
    private Speler speler;

//    public RoomManager(KamerFactory kamerFactory, Speler speler) {
//        this.kamerFactory = kamerFactory;
//        this.toegangsManager = new ToegangsManager();
//        this.inventoryManager = new InventoryManager(speler);
//
//        for (String key : kamerFactory.getKamerKeys()) {
//            Kamer kamer = kamerFactory.getKamer(key);
//            kamer.getDeur().setOpen(false);
//            kamers.add(kamer);
//        }
//    }

    public RoomManager(KamerFactory kamerFactory) {
        this.kamerFactory = kamerFactory;
        this.toegangsManager = new ToegangsManager();
        this.inventoryManager = null; // tijdelijk, want speler nog niet bekend

        for (String key : kamerFactory.getKamerKeys()) {
            Kamer kamer = kamerFactory.getKamer(key);
            kamer.getDeur().setOpen(false);
            kamers.add(kamer);
        }
    }

    public void setSpeler(Speler speler) {
        this.speler = speler;
        this.inventoryManager = new InventoryManager(speler); // pas hier maken
    }



    public List<Kamer> getBeschikbareKamers() {
        return kamers;
    }

    public Kamer getKamerOpPositie(int positie) {
        return kamers.get(positie);
    }

    public Kamer verwerkKamerCommando(String input) {
        String prefix = "ga naar kamer";
        if (input.length() <= prefix.length()) {
            System.out.println("Ongeldig commando, gebruik: 'ga naar kamer [nummer|naam]'");
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
            System.out.println("Finale kamer toegevoegd aan kamers lijst.");
            System.out.println();
        }

        speler.setPositie(kamers.indexOf(finale));
        System.out.println("Speler positie naar finale kamer: " + speler.getPositie());
        System.out.println();

        return finale;
    }

    public void verwerkPak(String input) {
        String itemInput = input.substring(4).trim();
        Kamer kamer = getKamerOpPositie(speler.getPositie());
        List<Item> kamerItems = kamer.getItems();
        Item gekozenItem = null;

        try {
            int index = Integer.parseInt(itemInput) - 1;
            if (index >= 0 && index < kamerItems.size()) {
                gekozenItem = kamerItems.remove(index);
            } else {
                System.out.println("❌ Ongeldig itemnummer.");
                System.out.println();
                return;
            }
        } catch (NumberFormatException e) {
            gekozenItem = kamer.neemItem(itemInput);
        }

        if (gekozenItem != null) {
            inventoryManager.voegItemToe(gekozenItem);
        } else if (!itemInput.matches("\\d+")) {
            System.out.println("❌ Dat item is niet gevonden in deze kamer.");
            System.out.println();
        }
    }
}