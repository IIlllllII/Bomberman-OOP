module uet.oop.bombermanoop {
    requires javafx.controls;
    requires javafx.fxml;


    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;
}