package uet.oop.bomberman.core.scenes.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.core.HighScore;

import java.io.*;


public class Leaderboards extends VBox {
    private static int minScore = Integer.MAX_VALUE;
    private static final String fileName = "src/main/resources/data/highScore.txt";
    private static ObservableList<HighScore> highScoresList = null;

    public static void init() {
        if (highScoresList == null) {
            highScoresList = FXCollections.observableArrayList();
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    if (!file.createNewFile()) {
                        System.out.println("can't create file high score");
                    }
                }
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] data = line.split(";");
                    if (data.length < 4) {
                        break;
                    }
                    highScoresList.add(new HighScore(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3]));
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

    private static class SingletonHelper {
        private static final Leaderboards INSTANCE = new Leaderboards();
    }
    public static Leaderboards getInstance() {
        return Leaderboards.SingletonHelper.INSTANCE;
    }
    private Leaderboards() {
        setAlignment(Pos.CENTER);
        setSpacing(8);

        Text title = new Text("Leaderboards");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        title.setFill(Color.WHITE);

        TableView<HighScore> table = new TableView<>();
        table.setEffect(new DropShadow(20, Color.MEDIUMSPRINGGREEN));
        double DEFAULT_WIDTH = 400;
        table.setMaxWidth(DEFAULT_WIDTH);
        double DEFAULT_HEIGHT = 200;
        table.setMaxHeight(DEFAULT_HEIGHT);

        TableColumn<HighScore, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<HighScore, Integer> highScoreColumn = new TableColumn<>("High Score");
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        highScoreColumn.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<HighScore, String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<HighScore, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(nameColumn, highScoreColumn, levelColumn, dateColumn);
        table.setItems(highScoresList);
        table.getSortOrder().add(highScoreColumn);

        getChildren().addAll(title,table);
    }

    public static String to_String() {
        StringBuilder res = new StringBuilder();
        for (HighScore it : highScoresList) {
            res.append(it.getName()).append(";").append(it.getHighScore()).append(";")
                    .append(it.getLevel()).append(";").append(it.getDate()).append("\n");
        }
        return res.toString();
    }

    public static void add(HighScore score) {
        highScoresList.add(score);
        update();
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
}
