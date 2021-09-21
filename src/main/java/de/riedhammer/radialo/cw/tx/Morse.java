package de.riedhammer.radialo.cw.tx;

import de.riedhammer.radialo.audio.helper.MixerFetcher;
import de.riedhammer.radialo.cw.rx.sub.MorseDictionary;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Morse {

    private static final int DOT = 150, DASH = DOT * 3, FREQ = 500;

    public static void morse(String txt, String device) throws LineUnavailableException, InterruptedException {
        for (char c : txt.toUpperCase().toCharArray()) {
            for (char note : new MorseDictionary().translateBack(String.valueOf(c)).toCharArray()) {
                System.out.print(note == ' ' ? " " : note);
                if (note == ' ') continue;
                try (SourceDataLine sdl = MixerFetcher.getSourceDataLineFor(device)) {
                    sdl.open(sdl.getFormat());
                    sdl.start();
                    for (int i = 0; i < (note == '.' ? DOT : DASH) * 8; i++) {
                        sdl.write(new byte[]{(byte) (Math.sin(i / (8000F / FREQ) * 2.0 * Math.PI) * 127.0)}, 0, 1);
                    }
                    sdl.drain();
                }
                Thread.sleep(DOT / 2);
            }
            Thread.sleep(DOT * 5);
            System.out.print(' ');
        }
    }


}
