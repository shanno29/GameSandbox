package sample.core.base;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.core.state.Bounds;
import sample.core.util.TimerUtil;

public abstract class Driver {

    private final AnimationTimer animationTimer;
    public final Main parent;
    public final Scene scene;
    public final Group group;

    public abstract Bounds getBounds();

    public Driver(Main application) {
        this.parent = application;
        this.group = new Group();

        this.scene = new Scene(group, getBounds().width, getBounds().height);
        this.scene.addEventFilter(MouseEvent.ANY, this::onMouseEvent);
        this.scene.addEventFilter(KeyEvent.ANY, this::onKeyboardEvent);

        this.animationTimer = TimerUtil.make(getBounds().interval, this::loop);
    }

    public void start() {
        animationTimer.start();
    };

    public abstract void loop();
    public abstract void draw();

    // Keyboard
    public abstract void onKeyboardInput(KeyEvent event);

    public void onKeyboardEvent(KeyEvent event) {
        if (KeyCode.UNDEFINED.equals(event.getCode())) return;
        if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) return;
        System.out.println("[KeyboardEvent]: "+event.getCode());
        onKeyboardInput(event);
    }


    // Mouse
    public abstract void onMouseInput(MouseEvent event);

    public void onMouseEvent(MouseEvent event) {
        if(!event.isPrimaryButtonDown()) return;
        if (MouseEvent.MOUSE_RELEASED.equals(event.getEventType())) return;
        System.out.println("[MouseEvent]: X: "+event.getX()+" Y:"+event.getY());
        onMouseInput(event);
    }


    // View
    public void addView(Node node) {
        group.getChildren().remove(node);
        group.getChildren().add(node);
    }

}
