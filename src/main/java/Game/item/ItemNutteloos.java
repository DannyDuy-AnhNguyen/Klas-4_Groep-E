package Game.item;

public class ItemNutteloos extends Item implements Gebruikbaar {
    public ItemNutteloos(String naam) {
        super(naam);
    }

    @Override
    public void gebruik() {
        System.out.println("🪨 Je inspecteert de rots... hij doet niets.");
    }
}
