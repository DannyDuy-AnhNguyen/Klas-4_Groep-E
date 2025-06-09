package Game.antwoord;

public class AntwoordRetrospective implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("c")) {
                System.out.println("✅ Correct! Het doel van de Sprint Retrospective is verbeteren door terug te kijken op het proces.");
                return true;
            } else {
                System.out.println("❌ Fout! De retrospective gaat over procesverbetering, niet over het product.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                System.out.println("✅ Correct! De Sprint Retrospective vindt plaats na de Sprint Review.");
                return true;
            } else {
                System.out.println("❌ Fout! De retrospective gebeurt altijd aan het einde van een sprint.");
                return false;
            }
        } else {
            System.out.println("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
