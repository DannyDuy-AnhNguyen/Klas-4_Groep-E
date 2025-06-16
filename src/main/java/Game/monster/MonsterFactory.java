package Game.monster;

public class MonsterFactory {
    public static MonsterType maakMonster(String naam) {
        return switch (naam.toLowerCase()) {
            case "misverstand" -> new Misverstand();
            case "verlies van focus" -> new VerliesVanFocus();
            case "blame game" -> new BlameGame();
            case "scrum verwarring" -> new ScrumVerwarring();
            case "sprint confusie" -> new SprintConfusie();
            default -> null;
        };
    }
}
