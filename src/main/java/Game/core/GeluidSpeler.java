import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GeluidSpeler {

    public static void speelGeluid(String padNaarBestand) {
        try {
            File geluidBestand = new File(padNaarBestand);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(geluidBestand);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wacht tot geluid klaar is met afspelen
            while (clip.isRunning()) {
                Thread.sleep(50);
            }

            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
