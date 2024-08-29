package service;

import com.microsoft.cognitiveservices.speech.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.ConnectException;

public class SpeechAPI extends Service {
    private static SpeechConfig speechConfig;
    static {
        Dotenv dotenv = Dotenv.load();
        subscriptionKey = dotenv.get("SPEECH_SUBSCRIPTION_KEY");
        serviceRegion = dotenv.get("SPEECH_REGION");

        try {
            speechConfig = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getSpeechFromText(String text, String language) throws Exception {
        speechConfig.setSpeechSynthesisVoiceName(getLanguageCode(language));

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        } else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails
                    .fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
                if (cancellation.getErrorCode() == CancellationErrorCode.ConnectionFailure)
                    throw new ConnectException("There is no internet connection.");
                throw new Exception("There is an error, please try again.");
            }
        }
        speechSynthesizer.close();
        speechSynthesisResult.close();
    }

    private static String getLanguageCode(String language) {
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