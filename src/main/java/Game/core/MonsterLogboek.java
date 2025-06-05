package Game.core;

import java.util.List;

public class MonsterLogboek {
    private final Speler speler;

    public MonsterLogboek(Speler speler) {
        this.speler = speler;
    }

    public void voegToe(String monster) {
        if (!speler.getMonsters().contains(monster)) {
            speler.getMonsters().add(monster);
            speler.notifyObservers();
        }
    }

    public void verwijder(String monster) {
        speler.getMonsters().remove(monster);
        speler.notifyObservers();
    }
}