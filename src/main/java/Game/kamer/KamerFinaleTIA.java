package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.MonsterStrijdService;

public class KamerFinaleTIA extends Kamer {
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
//    private Status status;
    private KamerBetreed betreedHandler = new KamerBetreed();
    private final Antwoord antwoordStrategie;

    public KamerFinaleTIA(Antwoord antwoordStrategie) {
        super("Finale TIA Kamer – Waarom Scrum?", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
    }

    protected boolean verwerkAntwoord(String antwoord, int huidigeVraag) {
        return antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
    }

    @Override
    public void verhoogHuidigeVraag(){
        huidigeVraag++;
    }

    @Override
    public int getHuidigeVraag() {
        return huidigeVraag;
    }

    @Override
    public void toonHint(){
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Het is iemand die niet in de scrum werkt"));
        hintContext.voegHintToe(0, new FunnyHint("Scrum is een ultieme methode om milieu niet te vervuilen"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Scrum is officieel ontstaan in 1995."));
        hintContext.voegHintToe(1, new FunnyHint("5 jaar geleden voordat het jaar 2000 is geboren."));

        // 🎯 Hints voor vraag 2
        hintContext.voegHintToe(2, new HelpHint("Deze vraag is grappig bedoeld, maar Scrum is serieus."));
        hintContext.voegHintToe(2, new FunnyHint("IT'S ME, MARIO WAHOOOOO!"));

        // 🎯 Hints voor vraag 3
        hintContext.voegHintToe(3, new HelpHint("Sprint 0 wordt vaak gebruikt voor voorbereiding en planning."));
        hintContext.voegHintToe(3, new FunnyHint("3, 2, 1, NUUUULLLLL!!!!"));

        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.toonHelp();
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Het is iemand die niet in de scrum werkt");
        } else if (huidigeVraag == 1) {
            System.out.println("Scrum is officieel ontstaan in 1995.");
        } else if (huidigeVraag == 2) {
            System.out.println("Deze vraag is grappig bedoeld, maar Scrum is serieus.");
        } else if (huidigeVraag == 3) {
            System.out.println("Sprint 0 wordt vaak gebruikt voor voorbereiding en planning.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag){
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("1. Wat vind je van Scrum?");
                System.out.println("a) Uitstekend");
                System.out.println("b) Neutraal");
                System.out.println("c) Slecht");
            }
            case 1 -> {
                System.out.println("2. Uit welk jaar is Scrum ontstaan?");
                System.out.println("a) 1993");
                System.out.println("b) 1995");
                System.out.println("c) 2001");
                System.out.println("d) 2010");
            }
            case 2 -> {
                System.out.println("3. Is Scrum Scrumario?");
                System.out.println("a) Ja");
                System.out.println("b) Ja");
                System.out.println("c) Ja");
                System.out.println("d) Ja");
            }
            case 3 -> {
                System.out.println("4. Bij welke sprint hoort deze userstory?");
                System.out.println("(Typ je antwoord, bijvoorbeeld 'Sprint 0')");
            }
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler){
        betreedHandler.verwerkResultaat(correct, speler, this);
    }

    @Override
    public void betreed(Speler speler) {
        betreedHandler.betreed(this, speler);
    }

    @Override
    public int getKamerID() {
        return 6;
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        boolean correct = antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
        updateScore(correct, speler);
        return correct;
    }

    public boolean verwerkAntwoordOpenVraag(String antwoord) {
        return antwoord.matches("^(0|sprint 0|nul|sprintnul|sprint0)$");
    }

    @Override
    public void toonHelp() {
        betreedHandler.toonHelp();
    }

//    public void setStatus(Status status) {
//        this.status = status;
//    }
}
