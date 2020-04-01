package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.core.base.*;
import sample.game.menu.MenuDriver;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        show(new MenuDriver(this));
    }

    public void show(Driver driver) {
        stage.setMinHeight(driver.getBounds().height);
        stage.setMinWidth(driver.getBounds().width);
        stage.setTitle(driver.getBounds().title);
        stage.setScene(driver.scene);
        stage.show();

        driver.start();
    }

}
