package Game.core;

import Game.item.Item;
import Game.kamer.Kamer;
import Game.kamer.KamerFactory;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private final List<Kamer> kamers = new ArrayList<>();
    private final KamerFactory kamerFactory;

    public RoomManager(KamerFactory kamerFactory) {
        this.kamerFactory = kamerFactory;

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
        String argument = input.substring("ga naar kamer".length()).trim();
        Kamer gekozenKamer = null;

        try {
            int nummer = Integer.parseInt(argument) - 1;
            if (nummer >= 0 && nummer < kamers.size()) {
                gekozenKamer = kamers.get(nummer);
            }
        } catch (NumberFormatException e) {
            gekozenKamer = kamerFactory.getKamer(argument.replaceAll("\\s+", "").toLowerCase());
        }

        if (gekozenKamer == null) {
            System.out.println("Onbekende kamer: " + argument);
            return null;
        }

        if (!gekozenKamer.getDeur().isOpen()) {
            gekozenKamer.getDeur().setOpen(true);
            System.out.println("🔓 De deur naar '" + gekozenKamer.getNaam() + "' is geopend.");
        }

        if (!gekozenKamer.isVoltooid()) {
            speler.setPositie(kamers.indexOf(gekozenKamer));
            gekozenKamer.betreed(speler);
        } else {
            System.out.println("Deze kamer is al voltooid.");
        }

        return gekozenKamer;
    }

    public boolean alleNormaleKamersVoltooid() {
        for (Kamer kamer : kamers) {
            if (!kamer.getNaam().toLowerCase().contains("finale") && !kamer.isVoltooid()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinaleKamerVoltooid() {
        for (Kamer kamer : kamers) {
            if (kamer.getNaam().toLowerCase().contains("finale") && kamer.isVoltooid()) {
                return true;
            }
        }
        return false;
    }

    public Kamer activeerFinaleKamer(Speler speler) {
        Kamer finale = kamerFactory.getKamer("Finale TIA Kamer – Waarom Scrum?");
        if (!kamers.contains(finale)) {
            finale.getDeur().setOpen(true);
            kamers.add(finale);
            System.out.println("🏁 Alle kamers voltooid! De finale kamer is nu beschikbaar.");
        }

        speler.setPositie(kamers.indexOf(finale));
        System.out.println("➡️ Je gaat automatisch naar de finale kamer!");
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
}
