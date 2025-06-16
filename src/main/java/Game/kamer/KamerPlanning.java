package Game.kamer;

import Game.antwoord.Antwoord;
import Game.item.Item;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.Misverstand;
import Game.monster.MonsterStrijdService;

import java.util.Scanner;

public class KamerPlanning extends Kamer {
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private final Scanner scanner = new Scanner(System.in);
    private KamerBetreed betreedHandler = new KamerBetreed();

    public KamerPlanning(Antwoord antwoordStrategie) {
        super("Sprint Planning", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
        this.monster = new Misverstand();
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
        hintContext.voegHintToe(0, new HelpHint("Bij de Sprint Planning doet iedereen mee."));
        hintContext.voegHintToe(0, new FunnyHint("Sprint Planning is het plannen met iedereen om .. een dorp aan te vallen."));

        hintContext.voegHintToe(1, new HelpHint("In de sprint planning wordt gekeken wat het doel van de aankomende sprint is."));
        hintContext.voegHintToe(1, new FunnyHint("Om een doelpunt te maken moet je sprinten naar het doel om een doelpunt te scoren."));

        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.betreedIntro(this);
    }

    @Override
    public void verwerkFeedback(int vraag) {
        if (vraag == 0) {
            System.out.println("Bij de sprintplanning nemen alle betrokkenen deel aan wat er gepland gaat worden.");
        } else if (vraag == 1) {
            System.out.println("Tijdens de sprintplanning wordt vastgesteld wat het doel van de sprint is en welke backlog-items worden opgepakt.");
            System.out.println("Het team bepaalt ook de moeilijkheidsgraad van user stories met behulp van Sprint Poker.");
        }
    }

    @Override
    public void verwerkOpdracht(int vraag) {
        switch (vraag) {
            case 0 -> {
                System.out.println("Vraag 1: Wie neemt deel aan de Sprint Planning?");
                System.out.println("a) Alleen de Scrum Master");
                System.out.println("b) Product Owner en Scrum Master");
                System.out.println("c) Product Owner, Scum Master en het hele Development Team");
                System.out.println("d) Product Owner, Scrum Master en het hele Development Team");
            }
            case 1 -> {
                System.out.println("Vraag 2: Wat wordt er tijdens de Sprint Planning vastgesteld?");
                System.out.println("a) Welke teamleden vakantie hebben");
                System.out.println("b) Wat het doel van de sprint is en welke backlog-items worden opgepakt");
                System.out.println("c) Hoe de vorige sprint geëvalueerd is");
                System.out.println("d) Wat de vastgestelde items van de backlog zijn als de product owner tevreden is met het product");
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
        return 1;
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return antwoordStrategie.verwerkAntwoord(antwoord, huidigeVraag);
    }

    @Override
    public void toonHelp() {
        betreedHandler.toonHelp();
    }
}