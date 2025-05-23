package Game.antwoord;

// De strategy klasse voor KamerFinalTia
public class AntwoordFinaleTIA implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int vraagIndex) {
        if (vraagIndex == 0) {
            if (antwoord.equals("a")) {
                System.out.println("Correct! Antwoord bij vraag 1 is juist.");
                return true;
            } else {
                System.out.println("Fout! Probeer het opnieuw.");
                return false;
            }
        } else if (vraagIndex == 1) {
            if (antwoord.equals("b")) {
                System.out.println("Correct! Antwoord bij vraag 2 is juist.");
                return true;
            } else {
                System.out.println("Fout! Probeer het opnieuw.");
                return false;
            }
        } else if (vraagIndex == 2) {
            if (antwoord.matches("[a-d]")) {
                System.out.println("Correct! Antwoord bij vraag 3 is geaccepteerd.");
                return true;
            } else {
                System.out.println("Fout! Probeer het opnieuw.");
                return false;
            }
        }
        return false;
    }
}