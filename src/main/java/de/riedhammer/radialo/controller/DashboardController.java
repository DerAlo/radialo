package de.riedhammer.radialo.controller;

import de.riedhammer.radialo.cat.IC7300;
import de.riedhammer.radialo.cw.rx.MorseDecoder;
import de.riedhammer.radialo.cw.rx.sub.WavFileException;
import de.riedhammer.radialo.cw.tx.Morse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    final private IC7300 ic7300;
    private String inputDevice;
    private String ouputDevice;

    public DashboardController(IC7300 ic7300) {
        this.ic7300 = ic7300;
    }

    @GetMapping("/dashboard")
    public String getDashboard(
            final Model model
    ) throws LineUnavailableException {
        model.addAttribute(
                "mixer",
                Arrays
                        .stream(AudioSystem.getMixerInfo())
                        .filter(m -> !m.getName().startsWith("Port"))
                        .collect(Collectors.toList()));
/*        model.addAttribute("sourceLines", MixerFetcher.getSourceLines());
        model.addAttribute("targetLines", MixerFetcher.getTargetLines());*/


        return "dashboard";
    }

    @GetMapping("/setFreq")
    @ResponseBody
    public String setFreq(
            @RequestParam final long freq
    ) {
        ic7300.init();
        ic7300.setFreq();
        return "done";
    }

    @GetMapping("/getFreq")
    @ResponseBody
    public String getFreq(
    ) {
        ic7300.init();
        return ic7300.readFrequency();
    }

    @GetMapping("/getMorse")
    @ResponseBody
    public String getMorse(
    ) throws IOException, WavFileException {
        return MorseDecoder.recordAndDecode(10000, inputDevice);
    }

    @GetMapping("/setMorse")
    @ResponseBody
    public void setMorse(String txt
    ) throws LineUnavailableException, InterruptedException {
        Morse.morse(txt,ouputDevice);
    }

    @GetMapping("/testMorse")
    @ResponseBody
    public String testMorse(String txt
    ) throws IOException, WavFileException {
        return MorseDecoder.morseAndDecode(txt, inputDevice,ouputDevice);
    }

    @GetMapping("/useAudioIn")
    @ResponseBody
    public void useAudioIn(
            @RequestParam final String audio
    ) {
        System.out.println("in: " + audio);
        inputDevice=audio;
    }

    @GetMapping("/useAudioOut")
    @ResponseBody
    public void useAudioOut(
            @RequestParam final String audio
    ) {
        System.out.println("out: " + audio);
        ouputDevice=audio;
    }

}
