package Game.monster;

import Game.item.Item;

public interface MonsterType {
    String getNaam();
    String getBeschrijving();
    String getVraag();
    boolean beantwoordVraag(String antwoord);
    boolean verslaMetItem(Item item);
    boolean isVerslagen();
}
