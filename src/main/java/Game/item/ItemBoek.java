package Game.item;

import static Game.core.ConsoleKleuren.*;

public class ItemBoek extends Item{

    public ItemBoek(String naam){
        super(naam);
    }

    private static boolean storylineGetoond = false;

    public static void toonInfo(boolean eersteKeer) {
        if (eersteKeer && !storylineGetoond) {
            storylineGetoond = true;
            System.out.println();
            System.out.println(BRIGHT_GREEN + "Welkom in de eerste kamer! :D");
            System.out.println("Voordat je verder gaat, een korte uitleg over de items die je onderweg zult vinden:");
            System.out.println("Tip: Je kan op elk moment de informatie opvragen van de items met de commando 'info' " + RESET);
            System.out.println();
            toonItemUitleg();
        }
    }

    public static void commandoInfo() {
        System.out.println("Je raadpleegt je gids over de items...");
        toonItemUitleg();
    }

    private static void toonItemUitleg() {
        System.out.println();
        System.out.println("- Zwaard: Verslaat elk monster in één klap. Krachtig, maar mogelijk zeldzaam.");
        System.out.println("- Splitter: Je hoeft slechts de helft van de vragen correct te beantwoorden om een monster te verslaan.");
        System.out.println("- Rots: Een zware steen. Hij doet niets... althans, voor zover je weet.");
        System.out.println();
    }

    public static boolean isStorylineGetoond() {
        return storylineGetoond;
    }
}