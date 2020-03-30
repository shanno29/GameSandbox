package sample;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.core.Window;
import sample.driver.Driver;
import sample.driver.TetrisDriver;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        Driver driver = new TetrisDriver(stage);
        stage.addEventFilter(MouseEvent.ANY, driver::onMouseEvent);
        stage.addEventFilter(KeyEvent.ANY, driver::onKeyEvent);
        stage.setMinHeight(Window.height);
        stage.setMinWidth(Window.width);
        driver.start();
    }


}
