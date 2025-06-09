package Game.antwoord;

public class AntwoordScrumBoard implements Antwoord {

    @Override
    public boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        if (huidigeVraag == 0) {
            if (antwoord.equals("a")) {
                System.out.println("✅ Correct! De juiste volgorde is: Epics > Userstories > Taken.");
                return true;
            } else {
                System.out.println("❌ Fout! De juiste volgorde is: Epics > Userstories > Taken.");
                return false;
            }
        } else if (huidigeVraag == 1) {
            if (antwoord.equals("b")) {
                System.out.println("✅ Correct! Een volledig Scrumboard bevat: To Do, Doing, Testing en Done.");
                return true;
            } else {
                System.out.println("❌ Fout! Een compleet Scrumboard bevat alle stappen inclusief To Do en Testing.");
                return false;
            }
        } else {
            System.out.println("❌ Ongeldige vraagindex.");
            return false;
        }
    }
}
