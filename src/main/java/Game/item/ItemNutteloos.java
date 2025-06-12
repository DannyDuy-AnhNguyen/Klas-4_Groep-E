package Game.item;

import Game.core.TextPrinter;

public class ItemNutteloos extends Item implements Gebruikbaar {
    public ItemNutteloos(String naam) {
        super(naam);
    }

    @Override
    public void gebruik() {
        TextPrinter.print("🪨 Je inspecteert de rots... hij doet niets.");
    }
}
