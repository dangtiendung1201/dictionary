package process;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Main {

    // **
    // * Sort dict by key[0]
    // */
    public static JSONArray sort(JSONArray dict) {
        for (int i = 0; i < dict.size(); i++) {
            for (int j = i + 1; j < dict.size(); j++) {
                if (((JSONObject) dict.get(i)).keySet().toArray()[0].toString()
                        .compareTo(((JSONObject) dict.get(j)).keySet().toArray()[0].toString()) > 0) {
                    Object temp = dict.get(i);
                    dict.set(i, dict.get(j));
                    dict.set(j, temp);
                }
            }
        }
        return dict;
    }

    // **
    // * Write dict to "sorted.json"
    // */
    public static void write(JSONArray dict) {
        try {
            FileWriter file = new FileWriter("src\\process\\sorted.json");
            file.write(dict.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // **
    // * English word
    // */
    public static void printEnglishWord(JSONArray dict) {
        for (int i = 0; i < dict.size(); i++) {
            System.out.println(((JSONObject) dict.get(i)).keySet().toArray()[0].toString());
        }
    }

    public static void printInformation(JSONArray dict, int index) {
        JSONObject word = (JSONObject) dict.get(index);
        String wordName = word.keySet().toArray()[0].toString();
        JSONObject definition = (JSONObject) word.get(wordName);

        System.out.println("Speech: " + definition.get("speech"));
        System.out.println("Word type: " + definition.get("wordType"));
        System.out.println("Meaning: " + definition.get("meaning"));
        System.out.println("Related word: " + definition.get("relatedWord"));
    }

    public static void main(String[] args) {
        // read dict from json file
        JSONArray dict = new JSONArray();
        try {
            dict = (JSONArray) new JSONParser().parse(new FileReader("src\\process\\sorted.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // sort dict by key[0]
        // dict = sort(dict);
        // write(dict);

        
        printInformation(dict, 0);
    }
}
