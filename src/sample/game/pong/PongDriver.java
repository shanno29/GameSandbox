package sample.game.pong;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.core.base.Driver;
import sample.core.state.Bounds;

public class PongDriver extends Driver {

    public PongDriver(Main application) {
        super(application);
    }

    @Override
    public Bounds getBounds() {
        return Bounds.empty
                .withTitle("Pong")
                .withHeight(32 * 20)
                .withWidth(32 * 30)
                .withInterval(4)
                .withScale(32);
    }

    @Override
    public void loop() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void onKeyboardInput(KeyEvent event) {

    }

    @Override
    public void onMouseInput(MouseEvent event) {

    }
}
