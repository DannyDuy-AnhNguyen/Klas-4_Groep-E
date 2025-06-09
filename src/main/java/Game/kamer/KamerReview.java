package Game.kamer;

import Game.antwoord.Antwoord;
import Game.core.Speler;
import Game.core.Status;
import Game.hint.FunnyHint;
import Game.hint.HelpHint;
import Game.hint.HintContext;
import Game.monster.SprintConfusie;
import Game.monster.MonsterStrijdService;

public class KamerReview extends Kamer {
    private int huidigeVraag = 0;
    private final HintContext hintContext = new HintContext();
    private Status status;
    private KamerBetreed betreedHandler = new KamerBetreed();
    private final Antwoord antwoordStrategie;

    public KamerReview(Antwoord antwoordStrategie) {
        super("Sprint Review", antwoordStrategie);
        this.antwoordStrategie = antwoordStrategie;
        this.monster = new SprintConfusie();
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
        hintContext.voegHintToe(0, new HelpHint("Het begint altijd na de sprint"));
        hintContext.voegHintToe(0, new FunnyHint("Wordt je geinterviewed nadat je gesprint hebt voor de Olympische Spelen?"));

        // 🎯 Hints voor vraag 1
        hintContext.voegHintToe(1, new HelpHint("Na de sprint wordt er teruggekeken wat je afgelopen sprint gekeken wat je met je team gedaan hebt"));
        hintContext.voegHintToe(1, new FunnyHint("Om goed te kunnen sprinten, moet je naar de feedback van Usain Bolt luisteren. Hij is een lightning bolt⚡"));

        // 🎯 Hints voor vraag 2
        hintContext.voegHintToe(2, new HelpHint("Wat is nuttig om bij een review te houden?"));
        hintContext.voegHintToe(2, new FunnyHint("Sprint review geeft jou ook speciale superkracht om 'Transparant' te zijn!"));

        hintContext.toonWillekeurigeHint(getHuidigeVraag());
    }

    @Override
    public void betreedIntro() {
        betreedHandler.betreedIntro(this);
    }

    @Override
    public void verwerkFeedback(int huidigeVraag) {
        switch (huidigeVraag) {
            case 0 -> System.out.println("Sprintreview wordt met je product owner gehouden om te kijken waar de scrumteam tot nu toe uitgevoerd heeft en of het voldaan is.");
            case 1 -> System.out.println("Sprint Review zorgt ervoor dat de scrumteam de feedback verzameld en wat de Product Owner nu van het product vindt.");
            case 2 -> System.out.println("Dankzij Sprint Review weten beide kanten zowel de product owner als scrumteam wat er gedaan kan worden en wat er verwacht wordt.");
        }
    }

    @Override
    public void verwerkOpdracht(int huidigeVraag){
        switch (huidigeVraag) {
            case 0 -> {
                System.out.println("Wanneer wordt er een sprintreview gehouden?");
                System.out.println("a) Aan het begin van de sprint");
                System.out.println("b) Tijdens de sprint");
                System.out.println("c) Aan het einde van de sprint");
            }
            case 1 -> {
                System.out.println("Wat is het belangrijkste doel van de Sprint Review?");
                System.out.println("a) De Scrum Master evalueren");
                System.out.println("b) Het increment inspecteren en feedback verzamelen");
                System.out.println("c) De volgende sprint alvast plannen");
                System.out.println("d) Vorige sprint doornemen");
            }
            case 2 -> {
                System.out.println("De voordelen van een Sprint Review zijn...?");
                System.out.println("a) Meer vergaderingen = meer productiviteit");
                System.out.println("b) Transparantie, snelle feedback, alignment met stakeholders");
                System.out.println("c) Langer werken zonder pauzes");
                System.out.println("d) De product owner tevreden houden");
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
        return 2;
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