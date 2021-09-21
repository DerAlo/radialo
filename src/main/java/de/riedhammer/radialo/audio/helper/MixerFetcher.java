package de.riedhammer.radialo.audio.helper;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;


public class MixerFetcher {
    /*
        private static final AudioFormat af = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100.0F,
                16,
                2,
                2 * 2,
                44100.0F,
                false);*/
    private static final AudioFormat af = new AudioFormat(8000F, 8, 1, true, false);
    //AudioSystem.getSourceDataLine(new AudioFormat(8000F, 8, 1, true, false))
    private static final DataLine.Info targetDataLineInfo = new DataLine.Info(TargetDataLine.class, af);
    private static final DataLine.Info sourceDataLineInfo = new DataLine.Info(SourceDataLine.class, af);

    //"Mikrofon (HD Webcam C270)" / MICROPHONE
    public static TargetDataLine getTargetDataLineFor(String device) {
        Mixer portMixer = null;
        Mixer targetMixer = null;
        try {
            for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
                if (mi.getName().equals(device)) {
                    System.out.println("Trying to get portMixer for :" + mi.getName());
                    portMixer = getPortMixerInfoFor(mi);
                    if (portMixer != null) {
                        System.out.println(portMixer.getMixerInfo().toString());
                        targetMixer = AudioSystem.getMixer(mi);
                        break;
                    }
                }
            }
            if (targetMixer != null) {
                targetMixer.open();

                TargetDataLine targetDataLine = (TargetDataLine) targetMixer.getLine(targetDataLineInfo);
                System.out.println("Got TargetDataLine from :" + targetMixer.getMixerInfo().getName());


                return targetDataLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static SourceDataLine getSourceDataLineFor(String device) {

        Mixer portMixer = null;
        Mixer targetMixer = null;
        try {
            for (Mixer.Info mi : AudioSystem.getMixerInfo()) {
                if (mi.getName().equals(device)) {

                    portMixer = getPortMixerInfoFor(mi);
                    if (portMixer != null) {

                        targetMixer = AudioSystem.getMixer(mi);
                        break;
                    }
                }
            }
            if (targetMixer != null) {
                targetMixer.open();

                SourceDataLine sourceDataLine = (SourceDataLine) targetMixer.getLine(sourceDataLineInfo);



                return sourceDataLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Mixer getPortMixerInfoFor(Mixer.Info mixerInfo) {
        //Check this out for interest
        //http://www.java-forum.org/spiele-multimedia-programmierung/94699-java-sound-api-zuordnung-port-mixer-input-mixer.html
        try {
            // get the requested mixer
            Mixer targetMixer = AudioSystem.getMixer(mixerInfo);
            targetMixer.open();
            //Check if it supports the desired format
            if (targetMixer.isLineSupported(targetDataLineInfo) || targetMixer.isLineSupported(sourceDataLineInfo)) {

                //now go back and start again trying to match a mixer to a port
                //the only way I figured how is by matching name, because
                //the port mixer name is the same as the actual mixer with "Port " in front of it
                // there MUST be a better way
                for (Mixer.Info portMixerInfo : AudioSystem.getMixerInfo()) {
                    String port_string = "Port ";
                    if ((port_string + mixerInfo.getName()).contains(portMixerInfo.getName())) {

                        Mixer portMixer = AudioSystem.getMixer(portMixerInfo);
                        portMixer.open();
                        //now check the mixer has the right input type eg LINE_IN
                        portMixer.close();
                        targetMixer.close();
                        return portMixer;


                    }
                }
            }
            targetMixer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<Line> getTargetLines() throws LineUnavailableException {
        List<Line> res = new ArrayList<>();
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                Line line = m.getLine(lineInfo);
                res.add(line);
            }
        }
        return res;
    }

    public static List<Line> getSourceLines() throws LineUnavailableException {
        List<Line> res = new ArrayList<>();
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getSourceLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                Line line = m.getLine(lineInfo);
                res.add(line);
            }
        }
        return res;
    }
}
