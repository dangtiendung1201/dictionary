package service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.Scanner;

public class SpeechAPI extends Service {

    public static void main(String[] args) {
        SpeechAPI speech = new SpeechAPI();
        System.out.println("Enter a sentence you want to speak: ");
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        try {
            speech.speak(sentence);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void speak(String text) throws Exception {
        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us"
                        + ".cmu_us_kal.KevinVoiceDirectory");

        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
    }
}