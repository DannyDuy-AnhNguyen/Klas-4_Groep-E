package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.HintContext;
import Game.hint.HelpHint;
import Game.hint.FunnyHint;
import Game.monster.Misverstand;
import Game.monster.VerliesVanFocus;
import Game.monster.MonsterStrijdService;

public class KamerDailyScrum extends Kamer {
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private KamerBetreed betreedHandler = new KamerBetreed();

    public KamerDailyScrum(Antwoord antwoordStrategie) {
        super("Daily Scrum", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
        this.monster = new VerliesVanFocus();
        deur.setOpen(false);
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
    public void toonHint() {
        // 🎯 Hints voor vraag 0
        hintContext.voegHintToe(0, new HelpHint("Scrum kent maar een paar officiële rollen."));
        hintContext.voegHintToe(0, new FunnyHint("De projectleider zit waarschijnlijk koffie te drinken ergens."));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Een sprint is bedoeld om snel resultaat te boeken."));
        hintContext.voegHintToe(1, new FunnyHint("Als je denkt aan jaren... denk kleiner. Véél kleiner."));

        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.betreedIntro(this);
    }

    @Override
    public void verwerkFeedback(int vraagIndex) {
        switch (vraagIndex) {
            case 0 -> System.out.println("Een projectleider is geen officiële rol binnen Scrum.");
            case 1 ->
                    System.out.println("Het ontbrekende woord was inderdaad 'sprint'. De meeste sprints duren 1 tot 4 weken. Kort genoeg om snel te kunnen bijsturen.");
        }
    }

    @Override
    public void verwerkOpdracht(int vraagIndex) {
        switch (vraagIndex) {
            case 0 -> {
                System.out.println("Vraag 1: Welke van de volgende rollen bestaat niet binnen Scrum?");
                System.out.println("a) Projectleider");
                System.out.println("b) Scrum Master");
                System.out.println("c) Development Team");
                System.out.println("d) Product Owner");
            }
            case 1 -> {
                System.out.println("Vraag 2: Vul het ontbrekende woord in:");
                System.out.println("Een standaard ______ duurt meestal 1 tot 4 weken.");
            }
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler) {
        betreedHandler.verwerkResultaat(correct, speler, this);
    }

    @Override
    public void betreed(Speler speler) {
        betreedHandler.betreed(this, speler);
    }

    @Override
    public int getKamerID() {
        return 3;
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
    }

    @Override
    public void toonHelp() {
        betreedHandler.toonHelp();
    }

    //In Daily Scrum kamer kun je een KeyJoker gebruiken.
    //Daarom geeft die een boolean 'true' terug.
    @Override
    public boolean accepteertKeyJoker() {
        return true;
    }
}