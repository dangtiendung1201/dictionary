package service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SpeechAPI {
    private static String speechKey = "81baf70c342f475291fed4dcdb2d9c0c";
    private static String speechRegion = "southeastasia";

    public void speak(String text, String language) throws ExecutionException, InterruptedException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);

        speechConfig.setSpeechSynthesisVoiceName(getLanguageCode(language));

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        }
        else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
                throw new ExecutionException(new Throwable("CANCELED: Did you set the speech resource key and region values?"));
            }
        }
        speechSynthesizer.close();
        speechSynthesisResult.close();
    }

    public static void main(String[] args) {
        SpeechAPI speech = new SpeechAPI();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence to speak: ");
        String sentence = scanner.nextLine();
        try {
            speech.speak(sentence, "English");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en-US-AriaNeural";
            case "Vietnamese":
                return "vi-VN-HoaiMyNeural";
            default:
                return "";
        }
    }
}