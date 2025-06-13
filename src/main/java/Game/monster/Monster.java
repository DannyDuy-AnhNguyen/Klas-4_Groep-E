package Game.monster;

import Game.item.Item;

public abstract class Monster implements MonsterType {
    protected final String naam;
    protected final String beschrijving;
    protected final String vraag;
    protected final String juistAntwoord;
    protected boolean verslagen;

    //Elke monster heeft een eigen unieke eigenschap.
    public Monster(String naam, String beschrijving, String vraag, String juistAntwoord) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.vraag = vraag;
        this.juistAntwoord = juistAntwoord;
        this.verslagen = false;
    }

    //Elke monster heeft een eigen naam.
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

    //Activeert wanneer de speler een antwoord als input meegeeft bij de monster vragen.
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

    //Ook heb je in deze spel een item om de monster te verslaan.
    //Zie de klasse Item > ItemWapen
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