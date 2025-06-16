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

//    //Deze constructor wordt alleen gebruikt voor de test Roommanager
        public RoomManager(KamerFactory kamerFactory, Speler speler) {
        this.kamerFactory = kamerFactory;
        this.toegangsManager = new ToegangsManager();
        this.speler = speler;
        this.inventoryManager = new InventoryManager(speler);

        for (String key : kamerFactory.getKamerKeys()) {
            Kamer kamer = kamerFactory.getKamer(key);
            kamer.getDeur().setOpen(false);
            kamers.add(kamer);
        }
    }


//    public RoomManager(KamerFactory kamerFactory) {
//        this.kamerFactory = kamerFactory;
//        this.toegangsManager = new ToegangsManager();
//        this.inventoryManager = null;
//
//        for (String key : kamerFactory.getKamerKeys()) {
//            Kamer kamer = kamerFactory.getKamer(key);
//            kamer.getDeur().setOpen(false);
//            kamers.add(kamer);
//        }
//    }


    public void setSpeler(Speler speler) {
        this.speler = speler;
        this.inventoryManager = new InventoryManager(speler);
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
            return null;
        }

        String argument = input.substring(prefix.length()).trim();
        Kamer gekozenKamer = parseerKamer(argument);

        if (gekozenKamer == null) {
            System.out.println("Onbekende kamer: " + argument);
            return null;
        }

        if (speler.getVoltooideKamers().contains(gekozenKamer.getKamerID())) {
            System.out.println("Deze kamer is al voltooid. Kies een andere.");
            return null;
        }

        if (!toegangsManager.openDeurAlsMogelijk(speler, gekozenKamer)) {
            return null;
        }

        if (!speler.isJokerGekozen()) {
            gekozenKamer.initSpeler(speler, gekozenKamer);
            speler.setJokerGekozen(true);
        }

        return gekozenKamer;
    }

    private Kamer parseerKamer(String argument) {
        try {
            int nummer = Integer.parseInt(argument);
            for (Kamer kamer : kamers) {
                if (kamer.getKamerID() == nummer) {
                    return kamer;
                }
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

        if (!speler.getMonsters().isEmpty()) {
            System.out.println("⛔ Je hebt nog actieve monsters! Versla ze eerst met 'bestrijd monster' voordat je naar de finale kamer mag.");
            return null;
        }

        Kamer finale = kamerFactory.getKamer("Finale TIA Kamer – Waarom Scrum?");
        System.out.println("Activeer finale kamer: " + finale.getNaam());

        finale.getDeur().setOpen(true);

        if (!kamers.contains(finale)) {
            kamers.add(finale);
            System.out.println("Finale kamer toegevoegd aan kamers lijst.");
        }

        speler.setPositie(kamers.indexOf(finale));
        System.out.println("Speler positie naar finale kamer: " + speler.getPositie());

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
                return;
            }
        } catch (NumberFormatException e) {
            gekozenItem = kamer.neemItem(itemInput);
        }

        if (gekozenItem != null) {
            inventoryManager.voegItemToe(gekozenItem);
        } else if (!itemInput.matches("\\d+")) {
            System.out.println("❌ Dat item is niet gevonden in deze kamer.");
        }
    }

    public void syncVoltooideKamersMetSpeler() {
        for (Kamer kamer : kamers) {
            if (speler.getVoltooideKamers().contains(kamer.getKamerID())) {
                kamer.setVoltooid();
            }
        }
    }

}
