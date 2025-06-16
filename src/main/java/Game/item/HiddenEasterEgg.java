package Game.item;

import Game.core.Speler;

public class HiddenEasterEgg {

    public static void checkVoorVolleRotsInventory(Speler speler) {
        boolean alleRotsen = speler.getInventory().size() == 5 &&
                speler.getInventory().stream().allMatch(i -> i.getNaam().equalsIgnoreCase("Rots"));

        if (alleRotsen) {
            System.out.println(" EASTER EGG: Je inventory zit VOL met nutteloze rotsen. ");
        }
    }
}
