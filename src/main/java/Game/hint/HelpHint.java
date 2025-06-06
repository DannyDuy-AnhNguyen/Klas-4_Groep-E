package Game.hint;

public class HelpHint extends AbstractHint implements Hint {
    public HelpHint(String tekst) {
        super(tekst);
    }

    @Override
    public void toon() {
        System.out.println("📘 HINT: " + tekst);
    }
}
