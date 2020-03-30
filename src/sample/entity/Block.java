package sample.entity;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.function.Consumer;

public class Block {

    private final Rectangle rectangle;

    public Block(double x, double y, Paint color) {
        this.rectangle = new Rectangle(32, 32, color);
        this.getRectangle().setLayoutX(x);
        this.getRectangle().setLayoutY(y);
    }

    public Integer getX() { return (int) getRectangle().getLayoutX(); }
    public void setX(int x) { getRectangle().setLayoutX(x); }
    public static Consumer<Block> incX(int d) { return block -> block.setX(block.getX()+d); }

    public Integer getY() { return (int) getRectangle().getLayoutY(); }
    public void setY(int y) { getRectangle().setLayoutY(y); }
    public static Consumer<Block> incY(int d) { return block -> block.setY(block.getY()+d); }

    public static Block def(int x, int y, Paint color) { return new Block(x, y, color); }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
