package sample.game.tetris.entity;

import javafx.geometry.Side;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.core.state.Bounds;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static sample.core.util.FuncUtil.*;

public class Piece {
    private final Set<Rectangle> blocks;

    private Piece(Set<Rectangle> blocks) { this.blocks = blocks; }

    public static Piece def(Color color, Bounds bounds, Integer... req) {
        return new Piece(IntStream
            .iterate(0, (i) -> i < req.length, (i) -> i += 2).boxed()
            .map(i -> {
                Rectangle rectangle = new Rectangle();
                rectangle.setLayoutX(bounds.width/2.0);
                rectangle.setLayoutY(bounds.scale);
                rectangle.setHeight(bounds.scale);
                rectangle.setWidth(bounds.scale);
                rectangle.setFill(color);
                return rectangle;
            })
            .collect(Collectors.toSet())
        );
    }


    public void shiftX(Integer distance) {
        getRectangles().forEach(x -> x.setTranslateX(distance));
    }

    public void shiftY(Integer distance) {
        getRectangles().forEach(x -> x.setTranslateY(distance));
    }

    public Stream<Rectangle> getRectangles() { return blocks.stream(); }
    private Stream<Double> getXs() { return getRectangles().map(Rectangle::getX); }
    private Stream<Double> getYs() { return getRectangles().map(Rectangle::getY); }

    public boolean touching(List<Piece> pieces) {
        return pieces.stream().flatMap(Piece::getRectangles).anyMatch(rect -> Optional.of(rect)
            .filter(not(r -> getXs().anyMatch(Double.valueOf(r.getX())::equals)))
            .filter(not(r -> getYs().anyMatch(Double.valueOf(r.getY())::equals)))
            .isEmpty()
        );
    }

    public boolean touching(Side side, Bounds bounds) {
        return Optional.of(side)
                .filter(not(pred(Side.TOP::equals).and(__ ->  getYs().anyMatch(y -> y < 0))))
                .filter(not(pred(Side.LEFT::equals).and(__ -> getXs().anyMatch(x -> x < 0))))
                .filter(not(pred(Side.RIGHT::equals).and(__ -> getXs().anyMatch(x -> x >= bounds.width))))
                .filter(not(pred(Side.BOTTOM::equals).and(__ -> getYs().anyMatch(y -> y >= bounds.height))))
                .isEmpty();
    }

    public static Piece random(Piece currentPiece, Bounds bounds) {
        Piece nextPiece = getNextPiece(bounds);
        return currentPiece != nextPiece ? nextPiece : random(nextPiece, bounds);
    }

    public static Piece getNextPiece(Bounds bounds) {
        switch (new Random().nextInt(7)) {
            case 0: return pieceT(bounds);
            case 1: return pieceJ(bounds);
            case 2: return pieceL(bounds);
            case 3: return pieceO(bounds);
            case 4: return pieceS(bounds);
            case 5: return pieceZ(bounds);
            case 6: return pieceI(bounds);
            default: return getNextPiece(bounds);
        }
    }

    public static Piece pieceI(Bounds bounds) {
        return Piece.def(Color.CYAN, bounds, 32, 32, 32, 64, 32, 96, 32, 128);
    }

    public static Piece pieceO(Bounds bounds) {
        return Piece.def(Color.YELLOW, bounds, 32, 32, 32, 64, 64, 32, 64, 64);
    }

    public static Piece pieceJ(Bounds bounds) {
        return Piece.def(Color.BLUE, bounds, 32, 32, 32, 64, 32, 96, 64, 96);
    }

    public static Piece pieceL(Bounds bounds) {
        return Piece.def(Color.ORANGE, bounds, 64, 32, 64, 64, 64, 96, 32, 96);
    }

    public static Piece pieceS(Bounds bounds) {
        return Piece.def(Color.LIMEGREEN, bounds, 32, 32, 32, 64, 64, 64, 64, 96);
    }

    public static Piece pieceZ(Bounds bounds) {
        return Piece.def(Color.RED, bounds, 32, 32, 32, 64, 64, 64, 96, 64);
    }

    public static Piece pieceT(Bounds bounds) {
        return Piece.def(Color.PURPLE, bounds, 32, 32, 64, 64, 96, 32, 64, 32);
    }

}
