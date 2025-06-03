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
        String prefix = "ga naar kamer";
        if (input.length() <= prefix.length()) {
            System.out.println("Ongeldig commando, gebruik: 'ga naar kamer [nummer|naam]'");
            return null;
        }

        String argument = input.substring(prefix.length()).trim();
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
            if (speler.getSleutels() != 0) {
                speler.gebruikSleutel();
                gekozenKamer.getDeur().setOpen(true);
                System.out.println("🔓 De deur naar '" + gekozenKamer.getNaam() + "' is geopend.");

                //Bij deze code wordt gekeken of de speler al in die kamer de joker gekozen heeft
                //Als je de nieuwe kamer betreedt, krijg
                if (!speler.isJokerGekozen()) {
                    gekozenKamer.initSpeler(speler);
                    speler.setJokerGekozen(true);
                }
            } else {
                System.out.println("❌ Je hebt geen sleutels om deze deur te openen.");
                return null;  // Kamer niet betreden zonder sleutel
            }
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
        System.out.println("Activeer finale kamer: " + finale.getNaam());

        finale.getDeur().setOpen(true);

        if (!kamers.contains(finale)) {
            kamers.add(finale);
            System.out.println("Finale kamer toegevoegd aan kamers lijst.");
        } else {
            System.out.println("Finale kamer zat al in kamers lijst.");
        }

        speler.setPositie(kamers.indexOf(finale));
        System.out.println("Speler positie naar finale kamer: " + speler.getPositie());

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
