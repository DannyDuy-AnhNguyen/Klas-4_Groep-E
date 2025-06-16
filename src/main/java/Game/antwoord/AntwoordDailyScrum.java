package Game.antwoord;

// De strategy klasse voor KamerDailyScrum
public class AntwoordDailyScrum implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            return antwoord.equals("a");
        } else if (huidigeVraag == 1) {
            return antwoord.equals("sprint");
        } else {
            return false;
        }
    }
}