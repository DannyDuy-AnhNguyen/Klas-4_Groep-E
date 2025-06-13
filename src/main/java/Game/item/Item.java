package Game.item;

//De
public abstract class Item {
    protected String naam;

    public Item(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    @Override
    public String toString() {
        return naam;
    }
}
