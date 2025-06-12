package Game.core;

import Game.item.Gebruikbaar;
import Game.item.GebruiktVoorMonster;
import Game.item.Item;
import Game.item.VerandertAantalVragen;
import Game.core.Speler;

import java.util.List;

public class InventoryManager {
    private final Speler speler;

    public InventoryManager(Speler speler) {
        this.speler = speler;
    }

    public void voegItemToe(Item item) {
        List<Item> inventory = speler.getInventory();

        if (inventory.size() >= 5) {
            TextPrinter.print("❌ Je inventory zit vol (max 5 items).");
            System.out.println();
            return;
        }

        inventory.add(item);
        TextPrinter.print("👜 Je hebt het item '" + item.getNaam() + "' opgepakt.");
        System.out.println();
        speler.notifyObservers();

        if (inventory.size() == 5 && inventory.stream().allMatch(i -> i.getNaam().equalsIgnoreCase("Rots"))) {
            TextPrinter.print("🥚 EASTER EGG: Je inventory zit VOL met nutteloze rotsen. 🤡");
        }
    }

    public boolean gebruikItem(String naam) {
        Item item = speler.getInventory().stream()
                .filter(i -> i.getNaam().equalsIgnoreCase(naam))
                .findFirst()
                .orElse(null);

        if (item == null) {
            TextPrinter.print("❌ Je hebt het item '" + naam + "' niet.");
            System.out.println();
            return false;
        }

        System.out.println("🧪 Je gebruikt het item: " + item.getNaam());
        System.out.println();

        boolean gebruikt = false;

        if (item instanceof GebruiktVoorMonster wapen) {
            wapen.gebruikTegenMonster();
            gebruikt = true;
        } else if (item instanceof VerandertAantalVragen aanpassing) {
            aanpassing.pasAantalVragenAan(0);
            gebruikt = true;
        } else if (item instanceof Gebruikbaar gebruikbaarItem) {
            gebruikbaarItem.gebruik();
            gebruikt = true;
        } else {
            TextPrinter.print("❓ Geen effect bekend voor dit item.");
            System.out.println();
        }

        if (gebruikt) {
            speler.getInventory().remove(item);
            speler.notifyObservers();
        }

        return gebruikt;
    }

    public void toonInventory() {
        if (speler.getInventory().isEmpty()) {
            TextPrinter.print("📭 Je hebt geen items in je inventory.");
            System.out.println();
        } else {
            TextPrinter.print("📦 Je inventory bevat:");
            for (Item item : speler.getInventory()) {
                System.out.println("• " + item.getNaam());
            }
        }
    }
}
