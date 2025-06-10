package Game.stub;

import Game.core.Speler;

import java.util.List;

public class StubSpeler extends Speler {
    public StubSpeler() {
        super();                 // roept de no-arg constructor van Speler aan
        setNaam("TestSpeler");   // zet een vaste naam
    }

    @Override
    public int getScore() {
        return 100;
    }

    @Override
    public List<Integer> getVoltooideKamers() {
        // retourneer twee voltooide kamers (indices)
        return List.of(1, 2);
    }

    @Override
    public List<String> getMonsters() {
        // geen actieve monsters
        return List.of();
    }
}
