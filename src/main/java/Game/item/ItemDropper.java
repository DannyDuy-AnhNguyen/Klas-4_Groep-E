package Game.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//De Context Klasse voor de items.
public class ItemDropper {
    private static final Random random = new Random();

    public static List<Item> genereerItemsVoorKamer() {
        List<Item> drops = new ArrayList<>();

        // Bepaal het totaal aantal items (0 t/m 3)
        int totaalAantalItems = random.nextInt(4); // 0, 1, 2 of 3

        // Mogelijke dropopties
        List<Item> mogelijkeItems = new ArrayList<>();

        // 40% kans op nutteloos item
        if (random.nextDouble() < 0.4) {
            mogelijkeItems.add(new ItemNutteloos("Rots"));
        }

        // Voeg bruikbare items toe
        List<Item> bruikbareItems = List.of(
                new ItemWapen("Scrum Zwaard"),
                new ItemSplitter("Splitter"),
                new ItemVragenGum("VragenGum")
        );
        mogelijkeItems.addAll(bruikbareItems);

        // Schud en selecteer random aantal
        Collections.shuffle(mogelijkeItems);
        for (int i = 0; i < Math.min(totaalAantalItems, mogelijkeItems.size()); i++) {
            drops.add(mogelijkeItems.get(i));
        }

        return drops;
    }
}
