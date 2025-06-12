package Game.item;

import Game.core.TextPrinter;

public class ItemSplitter extends Item implements VerandertAantalVragen {
    public ItemSplitter(String naam) {
        super(naam);
    }

    @Override
    public int pasAantalVragenAan(int huidigAantal) {
        TextPrinter.print("🪓 Splitter geactiveerd. Je hoeft minder vragen te beantwoorden.");
        return Math.max(1, huidigAantal / 2);
    }
}
