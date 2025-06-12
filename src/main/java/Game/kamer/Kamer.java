package Game.kamer;

import Game.core.Deur;
import Game.item.Item;
import Game.core.Speler;
import Game.joker.HintJoker;
import Game.joker.KeyJoker;
import Game.core.Status;
import Game.antwoord.Antwoord;
import Game.hint.HintContext;
import Game.monster.Monster;
import Game.monster.MonsterStrijdService;
import Game.core.TextPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public abstract class Kamer {
    protected String naam;
    protected boolean voltooid = false;
    protected Deur deur;
    protected List<Item> items = new ArrayList<>();
    protected Scanner scanner;
    protected Status status;
    protected Antwoord antwoordStrategie;
    protected HintContext hintContext;
    protected Monster monster;
    private MonsterStrijdService monsterStrijdService;


    public Kamer(String naam, Antwoord antwoordStrategie) {
        this.naam = naam;
        this.antwoordStrategie = antwoordStrategie;
        this.deur = new Deur();
        this.scanner = new Scanner(System.in);
        this.monsterStrijdService = new MonsterStrijdService();
        deur.setOpen(true);
    }

    public abstract int getKamerID();

    public abstract int getHuidigeVraag();

    public abstract void verhoogHuidigeVraag();

    //Deze methode controleerd of de kamer wel of geen monster heeft (Alleen KamerFinaleTIA heeft geen monster)🔍
    public boolean heeftMonster() {
        return monster != null;
    }

    // Deze methode zorgt ervoor dat het binnen de kamerBetreed de monsters gebruikt kunt worden.
    // Zie methode binnen de klasse 'KamerBetreed' > verwerkResultaat
    public void bestrijdMonster(Speler speler) {
        if (heeftMonster()) {
            monsterStrijdService.bestrijdMonster(speler, monster, monster.getNaam());
        } else {
            TextPrinter.print("Deze kamer heeft geen monster!");
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Antwoord getAntwoordStrategie() {
        return antwoordStrategie;
    }

    public HintContext getHintContext() {
        return hintContext;
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
            TextPrinter.print("Goed gedaan! Je krijgt " + bonus + " punten. (Streak: " + speler.getStreak() + ")");
        } else {
            speler.verlaagScore(10);
            speler.setStreak(0);
            TextPrinter.print("Fout antwoord! Je verliest 10 punten en je streak is gereset.");
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

    //De sleutel wordt alleen gebruikt in de Daily Scrum en Review kamer als extra sleutel.
    public void geefExtraSleutel(Speler speler) {
        speler.voegSleutelToe(); // Spelerbeheer doet alles goed via Observer
        TextPrinter.print("🔑 Een extra sleutel is speciaal in de Daily Scrum kamer toegekend!");
    }

    // Bij init speler, controleer of KeyJoker in deze kamer überhaupt kan worden gebruikt
    public void initSpeler(Speler speler) {
        KeyJoker keyJoker = new KeyJoker("key");
        boolean keyToegestaan = keyJoker.canBeUsedIn(this);

        // Toon alleen keuzes die daadwerkelijk beschikbaar zijn
        if (keyToegestaan) {
            TextPrinter.print("🃏 Kies je joker: 'hint' of 'key'");
            String keuze = scanner.nextLine().trim().toLowerCase();
            if (keuze.equals("key")) {
                if (keyToegestaan) {
                    speler.voegJokerToe(keyJoker);
                    TextPrinter.print("🔐 Je hebt de Key joker gekozen. Succes!");
                } else {
                    TextPrinter.print("❌ De Key joker is niet beschikbaar in deze kamer.");
                    TextPrinter.print("ℹ️ Alleen beschikbaar in 'Daily Scrum' en 'Sprint Review'.");
                }
            } else if (keuze.equals("hint")) {
                speler.voegJokerToe(new HintJoker("hint"));
                TextPrinter.print("💡 Je hebt de Hint joker gekozen.");
            } else {
                TextPrinter.print("⚠️ Ongeldige keuze.");
            }
        } else {
            TextPrinter.print("🃏 Kies je joker: alleen 'hint' is beschikbaar in deze kamer.");
            speler.voegJokerToe(new HintJoker("hint"));
        }
    }

}