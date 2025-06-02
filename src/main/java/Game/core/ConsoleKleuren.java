package Game.core;

public class ConsoleKleuren {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

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
    public static void regenboogAnimatie(String tekst, int herhalingen, int vertraging) {
        String[] kleuren = {RED, YELLOW, GREEN, CYAN, BLUE, PURPLE};
        for (int i = 0; i < herhalingen; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < tekst.length(); j++) {
                // verschuif het kleurtje per frame
                sb.append(kleuren[(j + i) % kleuren.length])
                        .append(tekst.charAt(j));
            }
            sb.append(RESET);

            // print op dezelfde plek in de console
            System.out.print("\r" + sb.toString());
            try {
                Thread.sleep(vertraging);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Nieuwe regel na de animatie
    }
}
