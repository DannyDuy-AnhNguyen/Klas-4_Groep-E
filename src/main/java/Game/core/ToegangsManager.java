package Game.core;

import Game.kamer.Kamer;

public class ToegangsManager {

    public boolean openDeurAlsMogelijk(Speler speler, Kamer kamer) {
        if (kamer.getDeur().isOpen()) {
            return true;
        }

        if (speler.getSleutels() > 0) {
            speler.gebruikSleutel();
            kamer.getDeur().setOpen(true);
            System.out.println("🔓 De deur naar '" + kamer.getNaam() + "' is geopend.");
            System.out.println();
            return true;
        } else {
            System.out.println("❌ Je hebt geen sleutels om deze deur te openen.");
            System.out.println();
            return false;
        }
    }
}

