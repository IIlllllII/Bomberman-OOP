package uet.oop.bomberman.core;

public class HighScore {
    private String name;
    private String highScore;
    private String level;
    private String date;

    public HighScore(String name, int highScore, int level, String date) {
        this.name = name.trim();
        this.highScore = String.valueOf(highScore);
        this.level = String.valueOf(level);
        this.date = date.trim();
    }

    // Setter and getter to Leaderboard can read

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public String getHighScore() {
        return highScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
