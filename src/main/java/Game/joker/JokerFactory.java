package Game.joker;

//Voor deze klasse wordt het gebruikt voor de Database.
//De Database zorgt ervoor
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
