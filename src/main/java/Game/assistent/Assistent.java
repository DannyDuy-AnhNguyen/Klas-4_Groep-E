package Game.assistent;

public class Assistent {
    private final String hint;
    private final String hulpmiddel;
    private final String motivatie;

    public Assistent(String hint, String hulpmiddel, String motivatie) {
        this.hint = hint;
        this.hulpmiddel = hulpmiddel;
        this.motivatie = motivatie;
    }

    public void toonHint() {
        System.out.println(" Hint: " + hint);
    }

    public void toonHulpmiddel() {
        System.out.println(" Hulpmiddel: " + hulpmiddel);
    }

    public void toonMotivatie() {
        System.out.println(" " + motivatie);
    } 

    public void activeer() {
        toonHint();
        toonHulpmiddel();
        toonMotivatie();
    }

    public static Assistent maakVoorKamer(int kamerID) {
        if (kamerID == 1) { // KamerPlanning
            return new Assistent(
                    "Denk aan de Sprint Goal.",
                    " Stappenplan: Daily Scrum opzetten.",
                    "Je bent goed bezig als Scrum Master!"
            );
        } else if (kamerID == 2) { // KamerRetrospective
            return new Assistent(
                    "Focus op de backlog prioriteit.",
                    "Denk goed na duhh",
                    "Je denkt als een Product Owner!"
            );
        }
        return null;
    }
}