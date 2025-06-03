package Game.item;

public class ItemSplitter extends Item implements VerandertAantalVragen {
    public ItemSplitter(String naam, String effect){
        super(naam, effect);
    }

    @Override
    public int pasAantalVragenAan(int huidigAantal) {
        System.out.println("🪓 Splitter geactiveerd. Je hoeft minder vragen te beantwoorden.");
        return Math.max(1, huidigAantal / 2);
    }
}
