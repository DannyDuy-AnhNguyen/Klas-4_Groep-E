package Game.kamer;

import Game.core.Status;
import Game.hint.HintContext;
import Game.antwoord.Antwoord;
import Game.item.Item;
import Game.core.Deur;

import java.util.List;

public interface KamerInterface {

    void setStatus(Status status);

    int getHuidigeVraag();

    Status getStatus();

    HintContext getHintContext();

    Antwoord getAntwoordStrategie();

    int getKamerID();

    Deur getDeur();

    List<Item> getItems();

    Item neemItem(String naam);

    void verwerkOpdracht(int vraagNummer);

    void betreedIntro();

    void toonHelp();

    void verwerkResultaat(boolean correct, Game.core.Speler speler);

    boolean isVoltooid();

    void setVoltooid();
}