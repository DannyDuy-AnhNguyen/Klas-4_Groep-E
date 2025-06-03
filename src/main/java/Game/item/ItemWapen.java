package Game.item;

public class ItemWapen extends Item implements GebruiktVoorMonster {
    public ItemWapen(String naam, String effect){
        super(naam, effect);
    }

    @Override
    public void gebruikTegenMonster() {
        System.out.println("🗡️ Het zwaard verslaat het monster direct!");
    }
}
