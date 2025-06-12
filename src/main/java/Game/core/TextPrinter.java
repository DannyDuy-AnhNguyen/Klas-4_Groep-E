package Game.core;

public class TextPrinter {

    public static void printSlowly(String text, long delayInMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // voor nieuwe regel na tekst
    }

    // Voor standaard vertraging van bijvoorbeeld 20ms
    public static void print(String text) {
        printSlowly(text, 20);
    }
}