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
    private int sleutels = 1;
    private int levens = 3;
    private boolean jokerGekozen = false;
    private boolean eersteKamerBetreden = false;
    private int hintCounter = 0;
    // Deze variabele zorgt voor overzicht hoeveel hints de speler nog kan gebruiken.
    // Van deze variabele wordt bijgehouden hoe vaak de speler nog hints kunt gebruiken.
    private int hintsLeft = 4;
    private int keysLeft = 1;

    private final List<Integer> voltooideKamers = new ArrayList<>();
    private final List<String> monsters = new ArrayList<>();
    private final List<Item> inventory = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private final List<Joker> jokers = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    // Managers
    private final InventoryManager inventoryManager;
    private final LevenManager levenManager;
    private final JokerManager jokerManager;
    private final MonsterLogboek monsterLogboek;
    private final VoltooideKamersTracker kamerTracker;

    public Speler() {
        this.inventoryManager = new InventoryManager(this);
        this.levenManager = new LevenManager(this);
        this.jokerManager = new JokerManager(this);
        this.monsterLogboek = new MonsterLogboek(this);
        this.kamerTracker = new VoltooideKamersTracker(this);
    }

    // === Naam & positie ===
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
        notifyObservers();
    }

    public int getPositie() {
        return positie;
    }

    public void setPositie(int positie) {
        this.positie = positie;
        notifyObservers();
    }

    // === Score & streak ===
    public int getScore() {
        return score;
    }

    public void verhoogScore(int punten) {
        score += punten;
        notifyObservers();
    }

    public void verlaagScore(int punten) {
        score = Math.max(0, score - punten);
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

    public int getKeysLeft(){
        return keysLeft--;
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
        }
        return false;
    }

    // === Levenssysteem (via LevenManager) ===
    public int getLevens() {
        return levens;
    }

    public void setLevens(int levens) {
        this.levens = levens;
    }

    public void verliesLeven() {
        levenManager.verliesLeven();
    }

    public void resetLevens() {
        levenManager.resetLevens();
    }

    public void gameOver() {
        System.out.println("\n🛑 GAME OVER! Je hebt geen levens meer.");
        System.out.print("Wil je opnieuw beginnen? (ja/nee): ");
        String keuze = scanner.nextLine().trim().toLowerCase();

        if (keuze.equals("ja")) {
            clearConsole();
            System.out.println("🔄 Het spel wordt opnieuw gestart...\n");
            Game.Main.main(null);
        } else {
            System.out.println("""
        ╔══════════════════════════════╗
        ║    💀  YOU DIED, RIP...      ║
        ╚══════════════════════════════╝
        """);
            System.out.println();
            System.out.println();
            System.out.println("💀 Oei... Zelfs een Scrum Master kon je niet redden.");
            System.out.println();
            System.out.println();
            System.out.println("👋 Bedankt voor het spelen!");
            System.exit(0);
        }
    }

    // === Joker ===
    public boolean isJokerGekozen() {
        return jokerGekozen;
    }

    public void setJokerGekozen(boolean gekozen) {
        this.jokerGekozen = gekozen;
    }

    public boolean voegJokerToe(Joker joker) {
        return jokerManager.voegJokerToe(joker);
    }

    public List<Joker> getJokers() {
        return jokers;
    }


    // === hintJoker waar de hint gebruikt wordt ===
    // Van deze 2 onderstaande methodes wordt bijgehouden hoeveel hints de speler de hints gebruikt heeft.
    public void verhoogHintCounter() {
        hintCounter++;
    }

    public int getHintCounter() {
        return hintCounter;
    }

    // == Toont hoeveel hints de speler nog kunt gebruiken ==
    // Van deze 3 onderstaande methodes wordt bijgehouden hoe vaak de speler nog hints kunt gebruiken.
    public void verlaagHintCounter(){
        hintsLeft--;
    }


    public boolean gebruikHint() {
        if (hintsLeft > 0) {
            verlaagHintCounter();
            verhoogHintCounter();
            System.out.println("U heeft zoveel hints nog over📘: " + hintsLeft);;
            return true;
        }
        return false;
    }


    public int getHintsLeft(){
        return hintsLeft;
    }


    // === Inventory ===
    public void voegItemToe(Item item) {
        inventoryManager.voegItemToe(item);
    }

    public boolean gebruikItem(String naam) {
        return inventoryManager.gebruikItem(naam);
    }

    public void toonInventory() {
        inventoryManager.toonInventory();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    // === Kamers ===
    public void voegVoltooideKamerToe(int kamerIndex) {
        kamerTracker.voegKamerToe(kamerIndex);
    }

    public List<Integer> getVoltooideKamers() {
        return voltooideKamers;
    }

    // === Monsters ===
    public void voegMonsterToe(String monster) {
        monsterLogboek.voegToe(monster);
    }

    public void verwijderMonster(String monster) {
        monsterLogboek.verwijder(monster);
    }

    public List<String> getMonsters() {
        return monsters;
    }

    // === Observer ===
    public void voegObserverToe(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // === Eerste kamer tracker ===
    public boolean isEersteKamerBetreden() {
        return eersteKamerBetreden;
    }

    public void markeerEersteKamerBetreden() {
        this.eersteKamerBetreden = true;
    }

    // === Console clearen ===
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}