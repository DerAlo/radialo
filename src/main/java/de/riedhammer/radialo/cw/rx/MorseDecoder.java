package de.riedhammer.radialo.cw.rx;

import de.riedhammer.radialo.audio.helper.AudioRecorder;
import de.riedhammer.radialo.cw.rx.sub.MorseProcessor;
import de.riedhammer.radialo.cw.rx.sub.WavFileException;

import java.io.IOException;

public class MorseDecoder {

    public static String recordAndDecode(int time, String input) throws IOException, WavFileException {
        AudioRecorder.record(time, input);
        MorseProcessor m_proc = new MorseProcessor("rec.wav");
        m_proc.process();
        return m_proc.result();
    }

    public static String morseAndDecode(String txt, String input, String output) throws IOException, WavFileException {
        AudioRecorder.morseAndTestRecord(txt, input, output);
        MorseProcessor m_proc = new MorseProcessor("rec.wav");
        m_proc.process();
        return m_proc.result();
    }

/*    public static void main(String[] args) throws IOException, WavFileException {
        System.out.println(recordAndDecode(10000, "Mikrofon (HD Webcam C270)", "MICROPHONE"));
    }*/

}
