package sample.game.menu;

import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.Main;
import sample.core.base.Driver;
import sample.core.state.Bounds;
import sample.game.pong.PongDriver;
import sample.game.tetris.TetrisDriver;

public class MenuDriver extends Driver {

    public MenuDriver(Main application) {
        super(application);
    }

    @Override
    public Bounds getBounds() {
        return Bounds.empty
            .withHeight(32 * 30)
            .withWidth(32 * 20)
            .withTitle("Games")
            .withInterval(4)
            .withScale(32);
    }

    @Override
    public void start() {
        super.start();
        draw();

    }

    @Override
    public void loop() { }

    @Override
    public void draw() {
        Button aButton = new Button("Tetris");
        aButton.setOnMouseClicked(e -> {
            if (!MouseEvent.MOUSE_CLICKED.equals(e.getEventType())) return;
            System.out.println("Tetris Clicked");
            this.parent.show(new TetrisDriver(this.parent));
        });

        Button bButton = new Button("Pong");
        bButton.setOnMouseClicked(e -> {
            if (!MouseEvent.MOUSE_CLICKED.equals(e.getEventType())) return;
            System.out.println("Pong Clicked");
            this.parent.show(new PongDriver(this.parent));
        });

        VBox vPane = new VBox(aButton, bButton);
        addView(vPane);
    }

    @Override
    public void onKeyboardInput(KeyEvent event) {

    }

    @Override
    public void onMouseInput(MouseEvent event) {

    }
}
