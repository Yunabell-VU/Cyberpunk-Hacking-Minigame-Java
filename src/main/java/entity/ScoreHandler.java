package entity;

public class ScoreHandler {

    private ScoreHandler() { throw new IllegalStateException("Utility class"); }

    public static int importHighestScore(){
        return 100; //TODO
    }
    public static void exportHighestScore(int score){
        System.out.println("Score " + score + " saved");//TODO
    }
}
