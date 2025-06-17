package Game.joker;

import Game.core.Speler;
import Game.kamer.Kamer;

abstract public class AbstractJoker implements Joker{
    protected boolean used = false;
    protected String naam;

    //Elke joker sub klasse heeft ook een naam nodig.
    public AbstractJoker(String naam) {
        this.naam = naam;
    }

    @Override
    public void useIn(Kamer kamer, Speler speler){
        used = true;
    }

    //Deze methode wordt gebruikt in beide joker sub klasses🙂
    @Override
    public boolean isUsed() {
        return used;
    }

    //Deze methode wordt gebruikt in beide joker sub klasses🙂
    @Override
    public String getNaam() {
        return naam;
    }
}
