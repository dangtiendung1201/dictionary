package service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.net.ConnectException;
import java.util.concurrent.Future;

public class SpeechRecognitionAPI {
    private static String speechSubscriptionKey = "81baf70c342f475291fed4dcdb2d9c0c";
    private static String serviceRegion = "southeastasia";

    private static SpeechConfig config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
    private static AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();


    public static void main(String[] args) throws Exception {
        SpeechRecognitionAPI speech = new SpeechRecognitionAPI();
        System.out.println(speech.getSpeech("Vietnamese"));
    }

    public String getSpeech(String language) throws ConnectException, Exception {
        config.setSpeechRecognitionLanguage(getLanguageCode(language));
        SpeechRecognizer reco = new SpeechRecognizer(config, audioConfig);

        Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();

        SpeechRecognitionResult result = task.get();

        if (result.getReason() == ResultReason.RecognizedSpeech) {
            reco.close();
            return result.getText();
        } else if (result.getReason() == ResultReason.NoMatch) {
            reco.close();
            throw new Exception("Can't recognize speech.");
        } else if (result.getReason() == ResultReason.Canceled) {
            reco.close();
            CancellationDetails cancellation = CancellationDetails.fromResult(result);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                throw new ConnectException("No internet connection.");
            }
        }
        return "";
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en-US";
            case "Vietnamese":
                return "vi-VN";
            default:
                return "";
        }
    }
}