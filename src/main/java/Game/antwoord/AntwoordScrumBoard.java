package Game.antwoord;

import Game.core.TextPrinter;

public class AntwoordScrumBoard implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("a")) {
                TextPrinter.print("✅ Correct! De juiste volgorde is: Epics > Userstories > Taken.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! De juiste volgorde is: Epics > Userstories > Taken.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                TextPrinter.print("✅ Correct! Een volledig Scrumboard bevat: To Do, Doing, Testing en Done.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! Een compleet Scrumboard bevat alle stappen inclusief To Do en Testing.");
                return false;
            }
        } else {
            TextPrinter.print("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
