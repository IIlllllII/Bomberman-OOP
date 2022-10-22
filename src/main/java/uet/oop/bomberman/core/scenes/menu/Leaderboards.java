package uet.oop.bomberman.core.scenes.menu;

import uet.oop.bomberman.core.HighScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Leaderboards extends VBox {
    public Leaderboards(double prefWidth, double prefHeight) {
        setAlignment(Pos.CENTER);
        setSpacing(8);

        Text title = new Text("Leaderboards");
        title.setFont(new Font("/font1.ttf", 22));
        title.setStyle("-fx-font-weight: bold");
        title.setFill(Color.WHITE);

        TableView<HighScore> table = new TableView<>();
        table.setMaxWidth(prefWidth);
        table.setMaxHeight(prefHeight);

        TableColumn<HighScore, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<HighScore, String> highScoreColumn = new TableColumn<>("High Score");
        highScoreColumn.setSortType(TableColumn.SortType.ASCENDING);
        highScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        TableColumn<HighScore, String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<HighScore, String> timeFinishColumn = new TableColumn<>("Time Finish");
        timeFinishColumn.setCellValueFactory(new PropertyValueFactory<>("timeFinish"));

        TableColumn<HighScore, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        ObservableList<HighScore> highScoresList = FXCollections.observableArrayList(HighScore.getHIGH_SCORE());

        table.setItems(highScoresList);
        table.getColumns().addAll(nameColumn, highScoreColumn, levelColumn, timeFinishColumn, dateColumn);

        getChildren().addAll(title,table);
    }
}
