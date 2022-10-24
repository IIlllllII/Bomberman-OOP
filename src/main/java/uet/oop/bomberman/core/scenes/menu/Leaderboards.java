package uet.oop.bomberman.core.scenes.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.core.HighScore;

public class Leaderboards extends VBox {
    private final double DEFAULT_WIDTH = 400;
    private final double DEFAULT_HEIGHT = 200;

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
        table.setMaxWidth(DEFAULT_WIDTH);
        table.setMaxHeight(DEFAULT_HEIGHT);

        TableColumn<HighScore, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<HighScore, String> highScoreColumn = new TableColumn<>("High Score");
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        TableColumn<HighScore, String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<HighScore, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<HighScore> highScoresList = FXCollections.observableArrayList(HighScore.getHIGH_SCORE());

        table.setItems(highScoresList);
        table.getColumns().addAll(nameColumn, highScoreColumn, levelColumn, dateColumn);

        getChildren().addAll(title,table);
    }
}
