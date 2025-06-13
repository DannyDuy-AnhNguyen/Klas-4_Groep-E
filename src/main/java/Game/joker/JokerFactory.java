package Game.joker;

public class JokerFactory {
    public static Joker maakJoker(String naam, String kamerNaam) {
        if (naam == null) return null;
        return switch (naam.toLowerCase()) {
            case "hint" -> new HintJoker("hint");
            case "key" -> switch (kamerNaam.toLowerCase()) {
                case "daily scrum" -> new DailyScrumKeyJoker("key-daily");
                case "sprint review" -> new SprintReviewKeyJoker("key-review");
                default -> null;
            };
            default -> null;
        };
    }
}
