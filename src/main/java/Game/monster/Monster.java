package Game.monster;

import Game.item.Item;

public abstract class Monster implements MonsterType {
    protected final String naam;
    protected final String beschrijving;
    protected final String vraag;
    protected final String juistAntwoord;
    protected boolean verslagen;

    public Monster(String naam, String beschrijving, String vraag, String juistAntwoord) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.vraag = vraag;
        this.juistAntwoord = juistAntwoord;
        this.verslagen = false;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public String getBeschrijving() {
        return beschrijving;
    }

    @Override
    public String getVraag() {
        return vraag;
    }

    @Override
    public boolean beantwoordVraag(String antwoord) {
        if (antwoord.equalsIgnoreCase(juistAntwoord)) {
            verslagen = true;
            System.out.println("Je hebt het monster verslagen door de vraag correct te beantwoorden!");
            return true;
        } else {
            System.out.println("Fout antwoord. Het monster leeft nog!");
            return false;
        }
    }

    @Override
    public boolean verslaMetItem(Item item) {
        if (item.getNaam().equalsIgnoreCase("zwaard")) {
            verslagen = true;
            System.out.println("Je hebt het monster direct verslagen met een zwaard!");
            return true;
        } else {
            System.out.println("Dit item heeft geen effect op het monster.");
            return false;
        }
    }

    @Override
    public boolean isVerslagen() {
        return verslagen;
    }
}