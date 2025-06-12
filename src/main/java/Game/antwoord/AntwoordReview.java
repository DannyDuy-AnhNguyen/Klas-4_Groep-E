package Game.antwoord;

import Game.core.TextPrinter;

public class AntwoordReview implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("c")) {
                TextPrinter.print("✅ Correct! De Sprint Review vindt plaats aan het einde van de sprint.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! De Sprint Review komt altijd aan het einde, niet eerder.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                TextPrinter.print("✅ Correct! Tijdens de Review wordt het increment bekeken en feedback verzameld.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! Het draait om inspectie van het increment en waardevolle feedback.");
                return false;
            }
        } else if (huidigeVraag == 2) {
            if (antwoord.equals("b")) {
                TextPrinter.print("✅ Correct! Voordelen zijn transparantie, snelle feedback en alignment.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! Denk aan samenwerking en directe terugkoppeling.");
                return false;
            }
        } else {
            TextPrinter.print("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
