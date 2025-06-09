package Game.core;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class GeluidSpeler {

    public static void speelGeluid(String pad) {
        try (InputStream inputStream = GeluidSpeler.class.getClassLoader().getResourceAsStream(pad)) {
            if (inputStream == null) {
                System.err.println("❌ Geluidsbestand niet gevonden: " + pad);
                return;
            }

            AudioInputStream origineleStream = AudioSystem.getAudioInputStream(inputStream);
            AudioFormat baseFormat = origineleStream.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, origineleStream);

            Clip clip = AudioSystem.getClip();
            clip.open(decodedStream);
            clip.start();

            while (clip.isRunning()) {
                Thread.sleep(50);
            }

            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
