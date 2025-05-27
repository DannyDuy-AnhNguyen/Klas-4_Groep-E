package Game.core;

abstract public class Item {
    protected String naam;
    protected String effect; // Bijvoorbeeld: "hint", "kill", "nutteloos"

    //Wordt ook in constructor van de sub klasse opgeroepen.
    public Item(String naam, String effect) {
        this.naam = naam;
        this.effect = effect;
    }

    public String getNaam(){
        return naam;
    }

    public String getEffect() {
        return effect;
    }

    public void gebruik() {
        switch (effect) {
            case "kill" -> System.out.println("🗡️ Je gebruikt het Scrum Zwaard! Een monster is verslagen.");
            case "hint" -> System.out.println("💡 Je opent de Hint Scroll. Misschien helpt dit bij de volgende vraag.");
            case "halveer" -> System.out.println("🪓 Je gebruikt de Splitter! Het aantal monster-vragen wordt gehalveerd.");
            case "nutteloos" -> System.out.println("🪨 Je inspecteert de rots... hij doet niets.");
            default -> System.out.println("❓ Dit item heeft nog geen effect.");
        }
    }

    @Override
    public String toString() {
        return naam + " (" + effect + ")";
    }
}
