package Game.core;

import java.util.ArrayList;
import java.util.List;

public class Speler {
    private String naam;
    private int positie;
    private int score = 0;
    private int streak = 0;
    private int sleutels = 1;

    private List<Integer> voltooideKamers = new ArrayList<>();
    private List<String> monsters = new ArrayList<>();
    private List<Item> inventory = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();


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
            System.out.println("❌ Je hebt geen sleutels om deze deur te openen.");
            return false;
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

    public void gebruikItem(String naam) {
        Item item = inventory.stream()
                .filter(i -> i.getNaam().equalsIgnoreCase(naam))
                .findFirst()
                .orElse(null);

        if (item == null) {
            System.out.println("❌ Je hebt het item '" + naam + "' niet.");
            return;
        }

        System.out.println("🧪 Je gebruikt het item: " + item.getNaam() + " (" + item.getEffect() + ")");
        inventory.remove(item);
        notifyObservers();
    }

    // === Observer pattern ===
    //🙂Heb het in de if else gedaan, omdat het anders steeds de status steeds toevoegd,
    //😭maar het heeft ook impact op de items.
    public void voegObserverToe(Observer observer) {
        if(observers.isEmpty()){
            observers.add(observer);
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
