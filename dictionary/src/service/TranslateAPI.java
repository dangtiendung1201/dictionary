package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class TranslateAPI extends Service {
    private static int timeout; // Milliseconds
    private OkHttpClient client;

    public TranslateAPI() {
        subscriptionKey = "66380cc580084f81aa83c321e37fe0d0";
        serviceRegion = "eastasia";
        timeout = 10000;

        client = new OkHttpClient().newBuilder().connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS).build();
    }

    // This function performs a POST request.
    private String Post(String sentence, String originalLanguage, String translatedLanguage) throws IOException {
        String json = "[{\"Text\": \"" + sentence + "\"}]";
        RequestBody body = RequestBody.create(json.getBytes());
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=" + originalLanguage
                        + "&to=" + translatedLanguage)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                // location required if you're using a multi-service or regional (not global)
                // resource.
                .addHeader("Ocp-Apim-Subscription-Region", serviceRegion)
                .addHeader("Content-type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            System.out.println("Request timed out.");
            return "Ấn chậm thôi bạn êi.";
        } catch (InterruptedIOException e) {
            System.out.println("Request interrupted.");
            return "Ấn chậm thôi bạn êi.";
        }
    }

    private static String prettify(String json_text) {
        JsonElement json = JsonParser.parseString(json_text);
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String result = gson.toJson(json.getAsJsonArray().get(0).getAsJsonObject().get("translations").getAsJsonArray()
                .get(0).getAsJsonObject().get("text"));
        result = result.replace("\\n", "\n");
        return result.substring(1, result.length() - 1);
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en";
            case "Vietnamese":
                return "vi";
            default:
                return "";
        }
    }

    public String translate(String sentence, String originalLanguage, String translatedLanguage) throws IOException {
        return prettify(Post(sentence, getLanguageCode(originalLanguage), getLanguageCode(translatedLanguage)));
    }
}