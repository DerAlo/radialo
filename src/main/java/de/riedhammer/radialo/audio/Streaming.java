package de.riedhammer.radialo.audio;

import de.riedhammer.radialo.audio.helper.AudioOutputStream;

import javax.sound.sampled.*;
import java.io.OutputStream;

public class Streaming {


    private static AudioInputStream getAudioInputStream() {
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            TargetDataLine line = AudioSystem.getTargetDataLine(format);
            line.open(format);
            line.start();
            return new AudioInputStream(line);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static OutputStream getAudioOutputStream() {
        try {
            AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();
            return new AudioOutputStream(line);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}




