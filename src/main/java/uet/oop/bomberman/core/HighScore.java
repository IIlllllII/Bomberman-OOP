package uet.oop.bomberman.core;

public class HighScore {
    private final String name;
    private final Integer highScore;
    private final String level;
    private final String date;

    public HighScore(String name, int highScore, int level, String date) {
        this.name = name.trim();
        this.highScore = highScore;
        this.level = String.valueOf(level);
        this.date = date.trim();
    }

    // Setter and getter to Leaderboard can read

    public String getName() {
        return name;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }
}
