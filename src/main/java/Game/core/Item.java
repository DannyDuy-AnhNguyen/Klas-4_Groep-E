package Game.core;

public class Item {
    private String naam;
    private String effect; // Bijvoorbeeld: "hint", "kill", "nutteloos"

    public Item(String naam, String effect) {
        this.naam = naam;
        this.effect = effect;
    }

    public String getNaam() {
        return naam;
    }

    public String getEffect() {
        return effect;
    }

    public void gebruik() {
        switch (effect) {
            case "kill" -> System.out.println("🗡️ Je gebruikt het Scrum Zwaard! Een monster is verslagen.");
            case "hint" -> System.out.println("💡 Je opent de Hint Scroll. Misschien helpt dit bij de volgende vraag.");
            case "nutteloos" -> System.out.println("🪨 Je inspecteert de rots... hij doet niets.");
            default -> System.out.println("❓ Dit item heeft nog geen effect.");
        }
    }

    @Override
    public String toString() {
        return naam + " (" + effect + ")";
    }
}
