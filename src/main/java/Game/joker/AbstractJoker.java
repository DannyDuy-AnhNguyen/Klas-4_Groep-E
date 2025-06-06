package Game.joker;

public class AbstractJoker implements Joker{
    protected boolean used = false;
    private final String naam;

    //Elke joker sub klasse heeft ook een naam nodig.
    public AbstractJoker(String naam) {
        this.naam = naam;
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
