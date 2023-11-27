package service;

import static service.SpeechAPI.getSpeechFromText;

public class T2SThread extends Thread {
    public static String sentence = "";
    public static String language = "";

    public void getSpeechFromTextThread(String curSentence,String curLanguage) throws Exception {
        sentence = curSentence;
        language = curLanguage;
        start();

    }
    @Override
    public void run() {
        System.out.println("Thread is running");
        try {
            getSpeechFromText(sentence, language);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
