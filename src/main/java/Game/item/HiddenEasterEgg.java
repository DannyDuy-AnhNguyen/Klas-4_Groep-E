package Game.item;

import Game.core.TextPrinter;

import Game.core.Speler;

public class HiddenEasterEgg {

    public static void checkVoorVolleRotsInventory(Speler speler) {
        boolean alleRotsen = speler.getInventory().size() == 5 &&
                speler.getInventory().stream().allMatch(i -> i.getNaam().equalsIgnoreCase("Rots"));

        if (alleRotsen) {
            TextPrinter.print("🥚 EASTER EGG: Je inventory zit VOL met nutteloze rotsen. 🤡");
        }
    }
}
