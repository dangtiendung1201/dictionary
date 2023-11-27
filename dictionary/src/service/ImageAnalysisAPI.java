package service;

import com.azure.ai.vision.common.VisionServiceOptions;
import com.azure.ai.vision.common.VisionSource;
import com.azure.ai.vision.imageanalysis.*;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;

import static java.lang.Float.max;

public class ImageAnalysisAPI extends Service {
    private static VisionServiceOptions serviceOptions;

    static {
        subscriptionKey = "018dc14a6078443db9dacc4e4efb5c36";
        endpoint = "https://uetdic.cognitiveservices.azure.com/";

        try {
            serviceOptions = new VisionServiceOptions(new URL(endpoint), subscriptionKey);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getTextFromImage("E:\\anh.png"));
    }

    /**
     * Get text from image.
     * 
     * @param path: path of image file
     * @return text from image
     */
    public static String getTextFromImage(String path) throws Exception {
        String res = "";
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Float> endOfLines = new ArrayList<>();

        System.out.println(path);
        VisionSource imageSource = VisionSource.fromFile(path);

        ImageAnalysisOptions analysisOptions = new ImageAnalysisOptions();

        analysisOptions.setFeatures(EnumSet.of(ImageAnalysisFeature.CAPTION, ImageAnalysisFeature.TEXT));

        analysisOptions.setLanguage("en");

        analysisOptions.setGenderNeutralCaption(true);
        ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, imageSource, analysisOptions);

        ImageAnalysisResult result = analyzer.analyze();
        float maxLength = 0;
        if (result.getReason() == ImageAnalysisResultReason.ANALYZED) {
            if (result.getText() != null) {
                for (DetectedTextLine line : result.getText()) {
                    lines.add(line.getContent());
                    maxLength = max(maxLength, line.getBoundingPolygon().get(2));
                    endOfLines.add(line.getBoundingPolygon().get(2));
                }
            }
        } else {
            ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
            System.out.println(" Analysis failed.");
            System.out.println("   Error reason: " + errorDetails.getReason());
            System.out.println("   Error code: " + errorDetails.getErrorCode());
            System.out.println("   Error message: " + errorDetails.getMessage());
            if (errorDetails.getReason() == ImageAnalysisErrorReason.CONNECTION_FAILURE)
                throw new ConnectException("There is no internet connection.");
            throw new Exception("There is an error, please try again.");
        }
        for (int i = 0; i < lines.size(); ++i) {
            if (maxLength - endOfLines.get(i) > (float) 15 / 100 * maxLength) {
                res += lines.get(i) + "\n";
            } else {
                res += lines.get(i) + " ";
            }
        }
        return res;
    }
}