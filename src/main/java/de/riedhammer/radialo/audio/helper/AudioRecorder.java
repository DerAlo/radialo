package de.riedhammer.radialo.audio.helper;

import de.riedhammer.radialo.cw.tx.Morse;

import javax.sound.sampled.*;
import java.io.File;

public class AudioRecorder {
    // record duration, in milliseconds
    static final long RECORD_TIME = 10000;  // 10 sec

    // path of the wav file
    File wavFile = new File("rec.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Captures the sound and record into a WAV file
     */
    void start(String device) {
        try {
            AudioFormat format = getAudioFormat();
            line = MixerFetcher.getTargetDataLineFor(device);
            System.out.println("Start capturing...");
            line.open(format);
            line.start();
            AudioInputStream ais = new AudioInputStream(line);
            System.out.println("Start recording...");
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
    /*
     *//**
     * Entry to run the program
     *//*
    public static void main(String[] args) {
        final AudioRecorder recorder = new AudioRecorder();

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();

        // start recording
        recorder.start();
    }*/

    /**
     * Entry to run the program
     */
    public static void record(int time, String device) {
        final AudioRecorder recorder = new AudioRecorder();

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
        stopper.start();
        // start recording
        recorder.start(device);
    }


    public static void morseAndTestRecord(String txt, String input, String outout) {
        final AudioRecorder recorder = new AudioRecorder();

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    try {
                        Morse.morse(txt, outout);
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
        stopper.start();
        // start recording
        recorder.start(input);

    }


}

