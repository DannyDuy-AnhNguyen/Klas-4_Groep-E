package Game.stub;

import Game.core.Speler;

import java.util.List;

public class StubSpeler extends Speler {
    public StubSpeler() {
        super();
        setNaam("TestSpeler");   // zet een vaste naam
    }

    @Override
    public int getScore() {
        return 100;
    } // De Score Duhh

    @Override
    public List<Integer> getVoltooideKamers() {
        return List.of(1, 2);         //    de kamer die voltooid zijn

    }

    @Override
    public List<String> getMonsters() {
        return List.of(); // geen monsters
    }
}
