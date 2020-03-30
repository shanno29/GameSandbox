package sample.driver;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.core.TimerUtil;

import static javafx.scene.input.KeyEvent.KEY_RELEASED;


public abstract class Driver {

    final Stage stage;
    final Group group;
    final Scene scene;
    final AnimationTimer animationTimer;
    final String title;
    MouseEvent lastMouseEvent;

    public Driver(Stage stage, int interval, String title) {
        this.stage = stage;
        this.group = new Group();
        this.scene = new Scene(group, stage.getWidth(), stage.getHeight());
        this.animationTimer = TimerUtil.make(interval, this::loop);
        this.title = title;
        this.lastMouseEvent = null;
    }

    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        animationTimer.start();
    };

    public abstract void loop();

    public void onKeyEvent(KeyEvent event) {
        if (KeyCode.UNDEFINED.equals(event.getCode())) return;
        if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) return;
        System.out.println("[KeyboardEvent]: "+event.getCode());
        onKeyboardEvent(event);
    }

    public abstract void onKeyboardEvent(KeyEvent event);

    public void onMouseEvent(MouseEvent event) {
        if(!event.isPrimaryButtonDown()) return;
        if (lastMouseEvent != null && lastMouseEvent == event) return;
        this.lastMouseEvent = event;
        System.out.println("[MouseEvent]: X: "+event.getX()+" Y:"+event.getY());
    }

    void addView(Node node) { group.getChildren().add(node); }

}
