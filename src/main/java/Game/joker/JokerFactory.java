package Game.joker;

public class JokerFactory {
    public static Joker maakJoker(String naam) {
        if (naam == null) return null;
        return switch (naam.toLowerCase()) {
            case "hint" -> new HintJoker("hint");
            case "key" -> new KeyJoker("key");
            default -> null;
        };
    }
}
