package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.ScrumVerwarring;
import Game.monster.MonsterStrijdService;

public class KamerScrumBoard extends Kamer {
    private int huidigeVraag = 0;
    private Status status;
    private final HintContext hintContext = new HintContext();
    private ScrumVerwarring monster = new ScrumVerwarring();
    private KamerBetreed betreedHandler = new KamerBetreed();
    private final Antwoord antwoordStrategie;

    public KamerScrumBoard(Antwoord antwoordStrategie) {
        super("Scrum Board", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
        deur.setOpen(false);
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
        hintContext.voegHintToe(0, new HelpHint("Scrum-proces begint altijd eerst met epic."));
        hintContext.voegHintToe(0, new FunnyHint("Spiegeltje Spiegeltje aan de want, wie is het meest epic van het land?"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Scrumboard begint met de product backlog en dan nog een backlog."));
        hintContext.voegHintToe(1, new FunnyHint("Het product wordt geproduceerd en sprint sneller Usain Bolt. Gas gas gas!"));

        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.betreedIntro(this);
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("Epic is de deelonderdeel van een project onderwerp, Userstory de deel van je Epic en Taken de deel van de je userstory");
        } else if (huidigeVraag == 1) {
            System.out.println("Product backlog is waar alles userstories staan die nog gedaan moet worden,\nSprint backlog de userstories voor de sprint die je in deze sprint wilt maken,\n" +
                    "To Do wat er vandaag gedaan moet worden,\nDoing welke teamlid aan het uitvoeren is\nTesting wat nog getest moet worden\nDone wat er al gedaan is.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag) {
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("1. Wat is de volgorde om een Scrum-proces te maken?");
                System.out.println("a) Epics > Userstories > Taken");
                System.out.println("b) Epics > Taken > Userstories");
                System.out.println("c) Userstories > Epics > Taken");
            }
            case 1 -> {
                System.out.println("2. Welke borden gebruik je in het Scrumboard?");
                System.out.println("a) Product Backlog > Sprint Backlog > Doing > Testing > Done");
                System.out.println("b) Product Backlog > Sprint Backlog > To Do > Doing > Testing > Done");
                System.out.println("c) Sprint Backlog > To Do > Doing > Testing > Done");
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
        return 5;
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return false;
    }

    @Override
    public void toonHelp() {
        betreedHandler.toonHelp();
    }

    @Override
    public boolean heeftMonster() {
        return true;
    }

    public void bestrijdMonster(Speler speler) {
        MonsterStrijdService.bestrijdMonster(speler, monster, monster.getNaam());
    }
}