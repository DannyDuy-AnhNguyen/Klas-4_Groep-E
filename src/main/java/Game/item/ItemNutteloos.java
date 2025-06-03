package Game.item;

public class ItemNutteloos extends Item {
    public ItemNutteloos(String naam) {
        super(naam);
    }

    public void gebruik() {
        System.out.println("🪨 Je inspecteert de rots... hij doet niets.");
    }
}
