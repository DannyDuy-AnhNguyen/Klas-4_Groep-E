package Game.monster;

public class MonsterHelper {

    private final String[] juisteAntwoorden;

    public MonsterHelper(String[] juisteAntwoorden) {
        this.juisteAntwoorden = juisteAntwoorden;
    }

    public String getJuisteAntwoord(int index) {
        if (index >= 0 && index < juisteAntwoorden.length) {
            return juisteAntwoorden[index];
        } else {
            return "";
        }
    }
}