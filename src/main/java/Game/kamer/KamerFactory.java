package Game.kamer;

import Game.antwoord.*;
import Game.item.ItemDropper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KamerFactory {
    private final Map<String, Kamer> kamers = new HashMap<>();

    public KamerFactory() {
        Kamer planning = new KamerPlanning(new AntwoordPlanning());
        planning.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Sprint Planning"), planning);

        Kamer review = new KamerReview(new AntwoordReview());
        review.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Sprint Review"), review);

        Kamer daily = new KamerDailyScrum(new AntwoordDailyScrum());
        daily.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Daily Scrum"), daily);

        Kamer retrospective = new KamerRetrospective(new AntwoordRetrospective());
        retrospective.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Sprint Retrospective"), retrospective);

        Kamer scrumBoard = new KamerScrumBoard(new AntwoordScrumBoard());
        scrumBoard.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Sprint ScrumBoard"), scrumBoard);

        Kamer finale = new KamerFinaleTIA(new AntwoordFinaleTIA());
        finale.getItems().addAll(ItemDropper.genereerItemsVoorKamer());
        kamers.put(normaliseer("Finale TIA Kamer – Waarom Scrum?"), finale);
    }



    private String normaliseer(String s) {
        return s.toLowerCase().replaceAll("\\s+", "");
    }

    public Kamer getKamer(String key) {
        return kamers.get(normaliseer(key));
    }

    public List<String> getKamerKeys() {
        return List.of("Sprint Planning", "Sprint Review", "Daily Scrum", "Sprint Retrospective", "Sprint ScrumBoard");
    }
}
