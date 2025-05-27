package Game.kamer;

import Game.Deur;
import Game.core.Item;
import Game.core.Speler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Kamer {
    protected String naam;
    protected boolean voltooid = false;
    protected Deur deur;
    protected List<Item> items = new ArrayList<>();
    protected Scanner scanner;

    public Kamer(String naam) {
        this.naam = naam;
        this.deur = new Deur();
        this.scanner = new Scanner(System.in);
        deur.setOpen(true);
    }

    public String getNaam() {
        return naam;
    }

    public boolean isVoltooid() {
        return voltooid;
    }

    public void setVoltooid() {
        this.voltooid = true;
        deur.setOpen(true);
    }

    public Deur getDeur() {
        return deur;
    }

    // ✅ Item support
    public List<Item> getItems() {
        return items;
    }

//    public void voegItemToe(Item item) {
//        System.out.println("Items😏: ");
//        items.add(item);
//    }

    public Item neemItem(String naam) {
        for (Item i : items) {
            if (i.getNaam().equalsIgnoreCase(naam)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    // Abstracte methoden die concrete kamers moeten implementeren
    public abstract void betreedIntro(); // In plaats van KamerInfo() maken we gebruik van deze abstracte methode.
    public abstract void betreed(Speler speler);
    public abstract boolean verwerkAntwoord(String antwoord, Speler speler);
    public abstract void verwerkFeedback(int huidigeVraag);
    public abstract void verwerkOpdracht(int huidigeVraag);
    public abstract void verwerkResultaat(boolean antwoordstrategie, Speler speler);

    //Hint abstract method waar de hint objecten erin gezet wordt.
    public abstract void toonHint();

    public void updateScore(boolean correct, Speler speler) {
        if (correct) {
            speler.setStreak(speler.getStreak() + 1);
            int bonus = speler.getStreak() * 10;
            speler.verhoogScore(bonus);
            System.out.println("Goed gedaan! Je krijgt " + bonus + " punten. (Streak: " + speler.getStreak() + ")");
        } else {
            speler.verlaagScore(10);
            speler.setStreak(0);
            System.out.println("Fout antwoord! Je verliest 10 punten en je streak is gereset.");
        }
    }

    public void beurtVoltooid(boolean correct) {
        if (!correct) {
            deur.setOpen(false);
        }
    }

    public void toonHelp() {
        typeText("\n📜 Beschikbare commando's:", 30);
        typeText("- a / b / c / d     : Kies een antwoordoptie", 30);
        typeText("- help              : Toon deze uitleg", 30);
        typeText("- status            : Bekijk je huidige status", 30);
        typeText("- check             : Bekijk items in deze kamer", 30);
        typeText("- pak [item]        : Pak een item uit deze kamer", 30);
        typeText("- gebruik [item]    : Gebruik een item uit je inventory", 30);
        typeText("- naar kamer [x]    : Ga handmatig naar een andere kamer (als dit ondersteund is)\n", 30);
    }

    public void typeText(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}
