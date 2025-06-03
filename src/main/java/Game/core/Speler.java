package Game.core;

import Game.item.Item;
import Game.joker.Joker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Speler {
    private String naam;
    private int positie;
    private int score = 0;
    private int streak = 0;
    private int sleutels = 5;
    private int levens = 3;
    private static final int MAX_JOKERS = 2;
    private boolean jokerGekozen = false;
    private boolean eersteKamerBetreden = false;

    private List<Integer> voltooideKamers = new ArrayList<>();
    private List<String> monsters = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private List<Joker> jokers = new ArrayList<>();


    private Scanner scanner = new Scanner(System.in);

    // === Naam ===
    public void setNaam(String naam) {
        this.naam = naam;
        notifyObservers();
    }

    public String getNaam() {
        return naam;
    }

    // === Positie ===
    public void setPositie(int positie) {
        this.positie = positie;
        notifyObservers();
    }

    public int getPositie() {
        return positie;
    }

    // === Score en streak ===
    public int getScore() {
        return score;
    }

    public void verhoogScore(int punten) {
        score += punten;
        notifyObservers();
    }

    public void verlaagScore(int punten) {
        score -= punten;
        if (score < 0) {
            score = 0;
        }
        notifyObservers();
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
        notifyObservers();
    }

    // === Sleutelsysteem ===
    public int getSleutels() {
        return sleutels;
    }

    public void voegSleutelToe() {
        sleutels++;
        System.out.println("🔑 Je hebt een sleutel verdiend! Totaal sleutels: " + sleutels);
        notifyObservers();
    }

    public boolean gebruikSleutel() {
        if (sleutels > 0) {
            sleutels--;
            System.out.println("🔓 Sleutel gebruikt. Resterende sleutels: " + sleutels);
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    // === Levenssysteem ===
    public int getLevens() {
        return levens;
    }

    public void verliesLeven() {
        levens--;
        System.out.println("💔 Je hebt een leven verloren! Resterende levens: " + levens);
        notifyObservers();

        if (levens <= 0) {
            gameOver();
        }
    }

    public void resetLevens() {
        levens = 3;
        notifyObservers();
    }

    public boolean isJokerGekozen() {
        return jokerGekozen;
    }

    public void setJokerGekozen(boolean gekozen) {
        this.jokerGekozen = gekozen;
    }

    private void gameOver() {
        System.out.println("\n🛑 GAME OVER! Je hebt geen levens meer.");
        System.out.print("Wil je opnieuw beginnen? (ja/nee): ");
        String keuze = scanner.nextLine().trim().toLowerCase();

        if (keuze.equals("ja")) {
            clearConsole();  // console wissen
            System.out.println("🔄 Het spel wordt opnieuw gestart...");
            Game.Main.main(null);  // spel herstarten
            System.exit(0);
        } else {
            System.out.println("👋 Bedankt voor het spelen!");
            System.exit(0);
        }
    }

    // === Voltooide kamers ===
    public void voegVoltooideKamerToe(int kamerIndex) {
        if (!voltooideKamers.contains(kamerIndex)) {
            voltooideKamers.add(kamerIndex);
            notifyObservers();
        }
    }

    public List<Integer> getVoltooideKamers() {
        return voltooideKamers;
    }

    // === Monsters ===
    public void voegMonsterToe(String monster) {
        if (!monsters.contains(monster)) {
            monsters.add(monster);
            notifyObservers();
        }
    }

    public void verwijderMonster(String monster) {
        monsters.remove(monster);
        notifyObservers();
    }

    public List<String> getMonsters() {
        return monsters;
    }

    // === Inventory functionaliteit ===
    public void voegItemToe(Item item) {
        if (inventory.size() >= 5) {
            System.out.println("❌ Je inventory zit vol (max 5 items).");
            return;
        }

        inventory.add(item);
        System.out.println("👜 Je hebt het item '" + item.getNaam() + "' opgepakt.");
        notifyObservers();

        // 🥚 Easter Egg check
        if (inventory.size() == 5 && inventory.stream().allMatch(i -> i.getNaam().equalsIgnoreCase("Rots"))) {
            System.out.println("🥚 EASTER EGG: Je inventory zit VOL met nutteloze rotsen. 🤡");
        }
    }

    public boolean heeftItem(String naam) {
        return inventory.stream().anyMatch(i -> i.getNaam().equalsIgnoreCase(naam));
    }

    public boolean gebruikItem(String naam) {
        Item item = inventory.stream()
                .filter(i -> i.getNaam().equalsIgnoreCase(naam))
                .findFirst()
                .orElse(null);

        if (item == null) {
            System.out.println("❌ Je hebt het item '" + naam + "' niet.");
            return false;
        }

        System.out.println("🧪 Je gebruikt het item: " + item.getNaam() + " (" + item.getEffect() + ")");

        // ➕ Effect uitvoeren
        if (item.getEffect().equals("kill")) {
            System.out.println("🗡️ Het Scrum Zwaard is gebruikt! Een monster is verslagen.");
        } else if (item.getEffect().equals("hint")) {
            System.out.println("💡 Hint Scroll gebruikt! Misschien helpt het je straks.");
        } else if (item.getEffect().equals("nutteloos")) {
            System.out.println("🪨 Je gebruikt de rots... niets gebeurt.");
        } else if (item.getEffect().equals("halveer")) {
            System.out.println("🪓 De Splitter is gebruikt! Minder vragen om te beantwoorden.");
        } else {
            System.out.println("❓ Geen effect bekend voor dit item.");
        }

        inventory.remove(item);
        notifyObservers();
        return true;
    }

    // Zorgt ervoor dat in het spel de jokers kan gebruiken.

    //Jokers kan toegevoegd worden met behulp van polymorfisme
    public boolean voegJokerToe(Joker joker) {
        if (jokers.size() >= MAX_JOKERS) {
            System.out.println("❌ Je hebt al het maximum aantal jokers (" + MAX_JOKERS + ") bereikt.");
            return false;
        }
        jokers.add(joker);
        return true;
    }

    //Hier wordt een lijst met daarin de toegevoegde jokers meegegeven welke jokers de speler nu heeft.
    public List<Joker> getJokers() {
        return jokers;
    }

    // === Observer pattern ===
    public void voegObserverToe(Observer observer) {
        if (observers.isEmpty()) {
            observers.add(observer);
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public boolean isEersteKamerBetreden() {
        return eersteKamerBetreden;
    }

    public void markeerEersteKamerBetreden() {
        this.eersteKamerBetreden = true;
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Voor Linux/macOS/Unix
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Kon de console niet wissen.");
        }
    }
}