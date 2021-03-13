package entity;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class ScoreHandler {

    private ScoreHandler() { throw new IllegalStateException("Utility class"); }

    public static int importHighestScore(){
        int highestScore = 0;
        try {
            File saveFile = new File("./resources/save/score");
            Scanner reader = new Scanner(saveFile);
            while (reader.hasNext()) {
                String cookedScore = reader.nextLine();
                highestScore = parseScore(cookedScore);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurs when loading score");
            e.printStackTrace();
        }
        return highestScore;
    }

    public static void exportHighestScore(int score){
        try {
            FileWriter scoreWriter = new FileWriter("./resources/save/score");
            String cookedScore = encryptScore(score);
            scoreWriter.write(cookedScore);
            scoreWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurs when saving score");
            e.printStackTrace();
        }
    }

    private static String encryptScore(int score) {
        return Base64.getEncoder().encodeToString(String.valueOf(score).getBytes(StandardCharsets.UTF_8));
    }

    private static int parseScore(String score) {
        byte[] scoreBytes = Base64.getDecoder().decode(score);
        String scoreString = new String(scoreBytes, StandardCharsets.UTF_8);
        return Integer.parseInt(scoreString);
    }
}
