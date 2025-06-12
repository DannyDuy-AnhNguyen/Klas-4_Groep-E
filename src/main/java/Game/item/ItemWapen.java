package Game.item;

import Game.core.TextPrinter;

public class ItemWapen extends Item implements GebruiktVoorMonster {
    public ItemWapen(String naam) {
        super(naam);
    }

    @Override
    public void gebruikTegenMonster() {
        TextPrinter.print("🗡️ Het zwaard verslaat het monster direct!");
    }
}
