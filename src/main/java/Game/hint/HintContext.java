package Game.hint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintContext {
    private final List<Hint> vraag0Hints = new ArrayList<>();
    private final List<Hint> vraag1Hints = new ArrayList<>();
    private final Random random = new Random();

    public void voegHintToe(int vraagIndex, Hint hint) {
        if (vraagIndex == 0) {
            vraag0Hints.add(hint);
        } else if (vraagIndex == 1) {
            vraag1Hints.add(hint);
        } else {
            System.out.println("❌ Hint toevoegen voor deze vraag is nog niet ondersteund.");
        }
    }

    public void toonWillekeurigeHint(int vraagIndex) {
        List<Hint> hints = null;

        if (vraagIndex == 0) {
            hints = vraag0Hints;
        } else if (vraagIndex == 1) {
            hints = vraag1Hints;
        }

        if (hints == null || hints.isEmpty()) {
            System.out.println("💡 Geen hints beschikbaar voor deze vraag.");
            return;
        }

        Hint gekozenHint = hints.get(random.nextInt(hints.size()));
        gekozenHint.toon();
    }

    public void wisAlleHints() {
        vraag0Hints.clear();
        vraag1Hints.clear();
    }
}
