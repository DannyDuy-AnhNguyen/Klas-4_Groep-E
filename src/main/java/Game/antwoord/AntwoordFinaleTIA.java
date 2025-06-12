package Game.antwoord;

import Game.core.TextPrinter;

// De strategy klasse voor KamerFinalTia
public class AntwoordFinaleTIA implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int vraagIndex) {
        if (vraagIndex == 0) {
            if (antwoord.equals("a")) {
                TextPrinter.print("Correct! Antwoord bij vraag 1 is juist.");
                return true;
            } else {
                TextPrinter.print("Fout! Probeer het opnieuw.");
                return false;
            }
        } else if (vraagIndex == 1) {
            if (antwoord.equals("b")) {
                TextPrinter.print("Correct! Antwoord bij vraag 2 is juist.");
                return true;
            } else {
                TextPrinter.print("Fout! Probeer het opnieuw.");
                return false;
            }
        } else if (vraagIndex == 2) {
            if (antwoord.matches("[a-d]")) {
                TextPrinter.print("Correct! Antwoord bij vraag 3 is geaccepteerd.");
                return true;
            } else {
                TextPrinter.print("Fout! Probeer het opnieuw.");
                return false;
            }
        }
        return false;
    }
}