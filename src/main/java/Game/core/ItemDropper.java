package Game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ItemDropper {
    private static final Random random = new Random();

    public static List<Item> genereerItemsVoorKamer() {
        List<Item> drops = new ArrayList<>();

        // Bepaal het totaal aantal items (0 t/m 3)
        int totaalAantalItems = random.nextInt(4); // 0, 1, 2 of 3

        // Voeg met 40% kans een nutteloze rots toe
        List<Item> mogelijkeItems = new ArrayList<>();
        if (random.nextDouble() < 0.4) {
            mogelijkeItems.add(new Item("Rots", "nutteloos"));
        }

        // Voeg bruikbare items toe
        List<Item> bruikbareItems = List.of(
                new Item("Hint Scroll", "hint"),
                new Item("Scrum Zwaard", "kill")
                );
        mogelijkeItems.addAll(bruikbareItems);

        // Schud de lijst en selecteer maximaal [totaalAantalItems]
        Collections.shuffle(mogelijkeItems);
        for (int i = 0; i < Math.min(totaalAantalItems, mogelijkeItems.size()); i++) {
            drops.add(mogelijkeItems.get(i));
        }

        return drops;
    }
}