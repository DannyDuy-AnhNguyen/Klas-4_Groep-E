package Game.stub;

import Game.core.Speler;
import java.util.ArrayList;
import java.util.List;

public class StubSpeler extends Speler {

    public StubSpeler() {
        super(); // Zorg dat Speler een default constructor heeft
    }

    @Override
    public String getNaam() {
        return "StubSpeler";
    }

    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public List<Integer> getVoltooideKamers() {
        List<Integer> kamers = new ArrayList<>();
        kamers.add(1);
        kamers.add(2);
        return kamers;
    }

    @Override
    public List<String> getMonsters() {
        return new ArrayList<>(); // geen monsters
    }

    @Override
    public boolean gebruikHint() {
        return super.gebruikHint();
    }
}