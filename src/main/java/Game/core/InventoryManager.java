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
            System.out.println("❌ Je inventory zit vol (max 5 items).");
            return;
        }

        inventory.add(item);
        System.out.println("👜 Je hebt het item '" + item.getNaam() + "' opgepakt.");
        speler.notifyObservers();

        if (inventory.size() == 5 && inventory.stream().allMatch(i -> i.getNaam().equalsIgnoreCase("Rots"))) {
            System.out.println("🥚 EASTER EGG: Je inventory zit VOL met nutteloze rotsen. 🤡");
        }
    }

    public boolean gebruikItem(String naam) {
        Item item = speler.getInventory().stream()
                .filter(i -> i.getNaam().equalsIgnoreCase(naam))
                .findFirst()
                .orElse(null);

        if (item == null) {
            System.out.println("❌ Je hebt het item '" + naam + "' niet.");
            return false;
        }

        System.out.println("🧪 Je gebruikt het item: " + item.getNaam());

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
            System.out.println("❓ Geen effect bekend voor dit item.");
        }

        if (gebruikt) {
            speler.getInventory().remove(item);
            speler.notifyObservers();
        }

        return gebruikt;
    }

    public void toonInventory() {
        if (speler.getInventory().isEmpty()) {
            System.out.println("📭 Je hebt geen items in je inventory.");
        } else {
            System.out.println("📦 Je inventory bevat:");
            for (Item item : speler.getInventory()) {
                System.out.println("• " + item.getNaam());
            }
        }
    }
}
