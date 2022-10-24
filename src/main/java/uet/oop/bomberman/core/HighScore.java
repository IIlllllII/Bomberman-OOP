package uet.oop.bomberman.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScore {
    private static List<HighScore> HIGH_SCORE = null;
    private static int minScore = Integer.MAX_VALUE;
    private static final String fileName = "src/main/resources/data/highScore.txt";
    private String name;
    private String highScore;
    private String level;
    private String date;

    public static void init() {
        if (HIGH_SCORE == null) {
            HIGH_SCORE = new ArrayList<>();
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        System.out.println("can't creat file high score");
                    };
                }
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] data = line.split(";");
                    HIGH_SCORE.add(new HighScore(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3]));
                    if (Integer.parseInt(data[1]) < minScore) {
                        minScore = Integer.parseInt(data[1]);
                    }
                }
            } catch (IOException e) {
                System.out.println("High score");
                throw new RuntimeException(e);
            }
        }
    }

    public HighScore(String name, int highScore, int level, String date) {
        this.name = name.trim();
        this.highScore = String.valueOf(highScore);
        this.level = String.valueOf(level);
        this.date = date.trim();
    }

    public static String to_String() {
        StringBuilder res = new StringBuilder();
        for (HighScore it : HIGH_SCORE) {
            res.append(it.name).append(";").append(it.highScore).append(";").append(it.level)
                    .append(";").append(it.date).append("\n");
        }
        return res.toString();
    }

    public static void add(HighScore score) {
        if (HIGH_SCORE.size() < 5) {
            HIGH_SCORE.add(score);
        } else {
            if (Integer.parseInt(score.highScore) > minScore) {
                HIGH_SCORE.add(score);
            }
        }
    }

    /**
     * Write to file
     */
    public static void update() {
        try {
            // clear file and write again
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(to_String());
            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<HighScore> getHIGH_SCORE() {
        return HIGH_SCORE;
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
