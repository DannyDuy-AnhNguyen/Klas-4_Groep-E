package Game.antwoord;

public class AntwoordReview implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("c")) {
                System.out.println("✅ Correct! De Sprint Review vindt plaats aan het einde van de sprint.");
                return true;
            } else {
                System.out.println("❌ Fout! De Sprint Review komt altijd aan het einde, niet eerder.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                System.out.println("✅ Correct! Tijdens de Review wordt het increment bekeken en feedback verzameld.");
                return true;
            } else {
                System.out.println("❌ Fout! Het draait om inspectie van het increment en waardevolle feedback.");
                return false;
            }
        } else if (huidigeVraag == 2) {
            if (antwoord.equals("b")) {
                System.out.println("✅ Correct! Voordelen zijn transparantie, snelle feedback en alignment.");
                return true;
            } else {
                System.out.println("❌ Fout! Denk aan samenwerking en directe terugkoppeling.");
                return false;
            }
        } else {
            System.out.println("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
