package Game.core;

public class ConsoleKleuren {
    public static final String RESET = "\u001B[0m";

    // Standaard kleuren
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Felle kleuren (bright)
    public static final String BRIGHT_BLACK = "\u001B[90m";  // grijs
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";

    // Achtergrondkleuren
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";

    // Tekst stijlen
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String REVERSED = "\u001B[7m";

    /**
     * Geeft een regenboogkleurige weergave van de meegegeven tekst.
     * Elke letter krijgt een andere kleur uit de regenboog.
     *
     * @param tekst De tekst die je wilt inkleuren.
     * @return De tekst met regenboogkleurige ANSI-codes.
     */
    public static String regenboog(String tekst) {
        String[] kleuren = {RED, YELLOW, GREEN, CYAN, BLUE, PURPLE};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tekst.length(); i++) {
            sb.append(kleuren[i % kleuren.length]).append(tekst.charAt(i));
        }
        sb.append(RESET);
        return sb.toString();
    }

    /**
     * Simuleert een animatie met regenboogkleuren op tekst in de console.
     *
     * @param tekst      De tekst die geanimeerd wordt.
     * @param herhalingen Hoeveel keer de animatie herhaald wordt.
     * @param vertraging  De pauze (ms) tussen frames.
     */

    public static void regenboogAnimatie(String tekst, int herhalingen, int vertraging) {
        String[] kleuren = {RED, YELLOW, GREEN, CYAN, BLUE, PURPLE};
        for (int i = 0; i < herhalingen; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < tekst.length(); j++) {
                sb.append(kleuren[(j + i) % kleuren.length])
                        .append(tekst.charAt(j));
            }
            sb.append(RESET);
            System.out.print("\r" + sb.toString());
            try {
                Thread.sleep(vertraging);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}

// import regel: import static Game.core.ConsoleKleuren.*;
// copy paste: System.out.println(KLEURNAAM + "TESKT" + RESET);