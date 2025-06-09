package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.BlameGame;
import Game.monster.MonsterStrijdService;

import java.util.Scanner;

public class KamerRetrospective extends Kamer {
    private int huidigeVraag = 0;
    private Status status;
    private final HintContext hintContext = new HintContext();
    private boolean introGetoond = false;
    private final Scanner scanner = new Scanner(System.in);
    private KamerBetreed betreedHandler = new KamerBetreed();
    private final Antwoord antwoordStrategie;

    public KamerRetrospective(Antwoord antwoordStrategie) {
        super("Kamer Retrospective", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
        this.monster = new BlameGame();
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
        hintContext.voegHintToe(0, new HelpHint("Bij de Sprint Planning doet iedereen mee."));
        hintContext.voegHintToe(0, new FunnyHint("Sprint Planning is het plannen met iedereen om .. een dorp aan te vallen."));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("In de sprint planning wordt gekeken wat het doel de aankomende sprint"));
        hintContext.voegHintToe(1, new FunnyHint("Om een doelpunt te maken moet je sprinten naar het doel om een doelpunt te scoren."));
        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.betreedIntro(this);
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        if (huidigeVraag == 0) {
            System.out.println("RetroSpective zorgt ervoor dat de teamleden weten wat er goed gaat, beter kan en hoe ze het kunnen verbeteren.");
        } else if (huidigeVraag == 1) {
            System.out.println("Om te weten hoe de samenwerking daadwerkelijk gaat, is het verstandig om daar 1 tot 2 weken te geven of het beste na elke sprint.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag) {
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("Wat is het hoofddoel van de Sprint Retrospective?");
                System.out.println("a) De resultaten van het product demonstreren aan de klant.");
                System.out.println("b) De product backlog aanpassen.");
                System.out.println("c) Terugkijken op het proces en verbeteren waar mogelijk is.");
            }
            case 1 -> {
                System.out.println("Wanneer vindt de Sprint Retrospective plaats?");
                System.out.println("a) Aan het begin van de sprint");
                System.out.println("b) Direct na de Sprint Review, aan het einde van de sprint");
                System.out.println("c) Halverwege de Sprint");
            }
        }
    }

    @Override
    public void verwerkResultaat(boolean correct, Speler speler) {
        betreedHandler.verwerkResultaat(correct, speler, this);
    }
//    public void verwerkResultaat(boolean correct, Speler speler) {
//        if (correct) {
//            speler.verhoogScore(10);
//            verwerkFeedback(huidigeVraag);
//            huidigeVraag++;
//            System.out.println("\n✅ Correct! Je krijgt 10 punten.\n");
//        } else {
//            System.out.println("\n❌ Fout! Monster 'Blame Game' verschijnt!");
//            speler.voegMonsterToe("Blame Game");
//            bestrijdMonster(speler);
//        }
//    }

    @Override
    public void betreed(Speler speler) {
        betreedHandler.betreed(this, speler);
    }

    @Override
    public int getKamerID() {
        return 4;
    }

    @Override
    public boolean verwerkAntwoord(String antwoord, Speler speler) {
        return false;
    }

    @Override
    public void toonHelp() {
        betreedHandler.toonHelp();
    }
}
