package Game.hint;

import java.util.*;

public class HintContext {
    private final Map<Integer, List<Hint>> hintMap = new HashMap<>();
    private final Random random = new Random();


    // Hint toevoegen aan specifieke vraag
    public void voegHintToe(int vraagIndex, Hint hint) {
        hintMap.computeIfAbsent(vraagIndex, k -> new ArrayList<>()).add(hint);
    }

    // Willekeurige hint tonen voor een specifieke vraag
    public void toonWillekeurigeHint(int vraagIndex) {
        List<Hint> hints = hintMap.get(vraagIndex);
        if (hints == null || hints.isEmpty()) {
            System.out.println("💡 Geen hints beschikbaar voor deze vraag.");
            return;
        }

        Hint gekozenHint = hints.get(random.nextInt(hints.size()));
        gekozenHint.toon();
    }

    // Alle hints verwijderen, misschien voor later als we het nodig hebben
    public void wisAlleHints() {
        hintMap.clear();
    }
}
