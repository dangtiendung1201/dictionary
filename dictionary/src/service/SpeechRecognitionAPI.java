package service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.Future;

import static com.microsoft.cognitiveservices.speech.CancellationErrorCode.ConnectionFailure;

public class SpeechRecognitionAPI extends Service {
    private static SpeechConfig config;
    private static AudioConfig audioConfig;
    static {
        subscriptionKey = "81baf70c342f475291fed4dcdb2d9c0c";
        serviceRegion = "southeastasia";

        try {
            config = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion);
            audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getTextFromSpeech("Vietnamese"));
    }

    public static String getTextFromSpeech(String language) throws Exception {
        System.out.println("Say something...");
        config.setSpeechRecognitionLanguage(getLanguageCode(language));
        SpeechRecognizer reco = new SpeechRecognizer(config, audioConfig);

        Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();

        SpeechRecognitionResult result = task.get();

        if (result.getReason() == ResultReason.RecognizedSpeech) {
            reco.close();
            return result.getText();
        } else if (result.getReason() == ResultReason.NoMatch) {
            reco.close();
            throw new IOException("Can't recognize speech.");
        } else if (result.getReason() == ResultReason.Canceled) {
            reco.close();
            CancellationDetails cancellation = CancellationDetails.fromResult(result);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());

                if (cancellation.getErrorCode() == ConnectionFailure ) {
                    throw new ConnectException("There is no internet connection.");
                };
                throw new Exception("There is an error, please try again.");
            }
        }
        return "";
    }

    private static String getLanguageCode(String language) {
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