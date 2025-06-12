package Game.antwoord;

import Game.core.TextPrinter;

public class AntwoordRetrospective implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("c")) {
                TextPrinter.print("✅ Correct! Het doel van de Sprint Retrospective is verbeteren door terug te kijken op het proces.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! De retrospective gaat over procesverbetering, niet over het product.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                TextPrinter.print("✅ Correct! De Sprint Retrospective vindt plaats na de Sprint Review.");
                return true;
            } else {
                TextPrinter.print("❌ Fout! De retrospective gebeurt altijd aan het einde van een sprint.");
                return false;
            }
        } else {
            TextPrinter.print("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
