package service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class SpeechAPI extends Service {

    public void speak(String text) throws Exception {
        try {
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            Voice voice = VoiceManager.getInstance().getVoice("kevin16");
            voice.allocate();
            voice.speak(text);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ngu vl");
        }
    }

    public static void main(String[] args) {
        SpeechAPI speech = new SpeechAPI();
//        Voice
        int ntime = 3;
        while (ntime-- != 0) {
            try {
                speech.speak("How?");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}