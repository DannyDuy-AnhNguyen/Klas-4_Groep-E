package Game.monster;

public class MonsterFactory {
    public static MonsterType maakMonster(String naam) {
        return switch (naam.toLowerCase()) {
            case "misverstand", "misverstandmonster" -> new Misverstand();
            case "verlies van focus", "verliesvanfocus" -> new VerliesVanFocus();
            case "blame game", "blamegame" -> new BlameGame();
            case "scrum verwarring", "scrumverwarring" -> new ScrumVerwarring();
            case "sprint confusie", "sprintconfusie" -> new SprintConfusie();
            default -> null;
        };
    }
}
