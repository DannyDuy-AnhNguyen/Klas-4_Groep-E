package Game.hint;

public class FunnyHint extends AbstractHint implements Hint {
    public FunnyHint(String tekst) {
        super(tekst);
    }

    @Override
    public void toon() {
        System.out.println("😂 FUNNY HINT: " + tekst);
    }
}
