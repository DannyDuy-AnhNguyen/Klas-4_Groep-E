package Game.core;

import javax.sound.sampled.*;
import java.io.InputStream;

public class GeluidSpeler {
    public static void speelGeluid(String resourcePad) {
        System.out.println("🔍 Probeer geluid te laden van: " + resourcePad);

        try (InputStream input = GeluidSpeler.class.getClassLoader().getResourceAsStream(resourcePad)) {
            if (input == null) {
                System.err.println("❌ Bestand niet gevonden in resources: " + resourcePad);
                return;
            }

            TextPrinter.print("✅ Bestand gevonden. Probeer te decoderen...");

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(input);
            AudioFormat baseFormat = audioStream.getFormat();

            System.out.println("🎼 Origineel formaat: " + baseFormat);

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            TextPrinter.print("🔊 Geluid afspelen...");

            Clip clip = AudioSystem.getClip();
            clip.open(decodedStream);
            clip.start();

            while (clip.isRunning()) {
                Thread.sleep(50);
            }

            clip.close();
            TextPrinter.print("✅ Geluid klaar.");
        } catch (Exception e) {
            System.err.println("❌ Fout tijdens afspelen:");
            e.printStackTrace();
        }
    }
}
