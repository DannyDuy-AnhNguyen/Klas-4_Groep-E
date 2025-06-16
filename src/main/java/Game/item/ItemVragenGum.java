package Game.item;

public class ItemVragenGum extends Item implements VerandertAantalVragen {
    public ItemVragenGum(String naam) {
        super(naam);
    }

    @Override
    public int pasAantalVragenAan(int huidigAantal) {
        System.out.println("VragenGum geactiveerd. Er wordt 1 vraag minder gesteld.");
        return Math.max(1, huidigAantal - 1);
    }
}
