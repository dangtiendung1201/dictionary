package service;

import java.io.IOException;
import java.util.Scanner;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TranslateAPI extends Service {
    private static String key = "66380cc580084f81aa83c321e37fe0d0";
    private static String location = "eastasia";

    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    public String Post(String sentence, String originalLanguage, String translatedLanguage) throws IOException {
        String json = "[{\"Text\": \"" + sentence + "\"}]";
        RequestBody body = RequestBody.create(json.getBytes());
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=" + originalLanguage
                        + "&to=" + translatedLanguage)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // location required if you're using a multi-service or regional (not global) resource. 
                .addHeader("Ocp-Apim-Subscription-Region", location) 
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonElement json = JsonParser.parseString(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(json.getAsJsonArray().get(0).getAsJsonObject().get("translations").getAsJsonArray().get(0).getAsJsonObject().get("text"));
        return result.substring(1, result.length() - 1);
    }

    public static void main(String[] args) {
        try {
            TranslateAPI translateRequest = new TranslateAPI();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a sentence to translate: ");
            String sentence = scanner.nextLine();
            String response = translateRequest.Post(sentence, "en", "vi");
            System.out.println(prettify(response));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}