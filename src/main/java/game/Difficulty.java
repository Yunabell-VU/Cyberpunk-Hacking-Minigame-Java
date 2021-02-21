package game;

import java.io.Serializable;

class Difficulty implements Serializable {
    private String level;
    private int initTimeLimit;
    private int bufferOffset;
    private int scoreReward;
    private int timeReward;
    private final int timePunishment;

    public Difficulty() {
        level = "NORMAL";
        initTimeLimit = 15;
        bufferOffset = 0;
        scoreReward = 35;
        timeReward = 10;
        timePunishment = -5;
    }

    public int getBufferOffset() {
        return bufferOffset;
    }

    public int getInitTimeLimit() {
        return initTimeLimit;
    }

    public int getScoreReward() {
        return scoreReward;
    }

    public int getTimeReward() {
        return timeReward;
    }
    public int getTimePunishment(){
        return  timePunishment;
    }

    public String getLevel() {
        return level;
    }

    public void setDifficulty(String level){
        switch (level){
            case "VERY EASY":
                this.setDifficultyVeryEasy();
                break;
            case "EASY":
                this.setDifficultyEasy();
                break;
            case "HARD":
                this.setDifficultyHard();
                break;
            default:
                this.setDifficultyNormal();
                break;
        }
    }

    private void setDifficultyVeryEasy(){
        this.level = "VERY EASY";
        this.initTimeLimit = 30;
        this.bufferOffset = 3;
        this.scoreReward = 15;
        this.timeReward = 15;
    }
    private void setDifficultyEasy(){
        this.level = "EASY";
        this.initTimeLimit = 20;
        this.bufferOffset = 2;
        this.scoreReward = 25;
        this.timeReward = 10;
    }
    private void setDifficultyNormal(){
        this.level = "NORMAL";
        this.initTimeLimit = 15;
        this.bufferOffset = 0;
        this.scoreReward = 35;
        this.timeReward = 10;
    }
    private void setDifficultyHard(){
        this.level = "HARD";
        this.initTimeLimit = 10;
        this.bufferOffset = 0;
        this.scoreReward = 50;
        this.timeReward = 5;
    }

    public String getDifficultyInfo() {
        String info;
        info = "<html> Initial Time Limit : "+ initTimeLimit + " seconds <br> Buffer Size : + "+ bufferOffset +
                "<br> Time Reward : "+ timeReward + " seconds <br> Score Reward : "+ scoreReward + "</html>";
        return info;
    }
}
