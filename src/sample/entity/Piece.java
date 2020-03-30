package sample.entity;

import javafx.scene.paint.Color;
import sample.core.Side;
import sample.core.Window;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Piece {
    public final Set<Block> blocks;

    public Piece(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public static Piece def(Color color, Integer... req) {
        return new Piece(IntStream
                .iterate(0, (i) -> i < req.length, (i) -> i+2).boxed()
                .map(i -> Block.def(req[i], req[i+1], color))
                .collect(Collectors.toSet())
        );
    }

    public void translateX(int d) { blocks.forEach(Block.incX(d)); }
    public void translateY(int d) { blocks.forEach(Block.incY(d)); }

    public boolean touching(List<Piece> oldPieces) {
        return oldPieces.stream().map(p -> p.blocks).flatMap(Collection::stream)
                .anyMatch(oldBlock -> predicate(Objects::nonNull)
                    .or(__ -> blocks.stream().map(Block::getX).anyMatch(oldBlock.getX()::equals))
                    .or(__ -> blocks.stream().map(Block::getY).anyMatch(oldBlock.getY()::equals))
                    .test(oldBlock)
                );

    }

    public static Consumer<Piece> shiftX(Integer distance) { return p -> p.translateX(distance); }

    public static Consumer<Piece> shiftY(Integer distance) { return p -> p.translateY(distance); }

    public boolean touching(Side side) {
        return Optional.of(side)
            .filter(predicate(Objects::nonNull))
            .filter(not(predicate(Side.Top::equals).and(__ ->  getYs().anyMatch(y -> y < 0))))
            .filter(not(predicate(Side.Left::equals).and(__ -> getXs().anyMatch(x -> x < 0))))
            .filter(not(predicate(Side.Right::equals).and(__ -> getXs().anyMatch(x -> x >= Window.width))))
            .filter(not(predicate(Side.Bottom::equals).and(__ -> getYs().anyMatch(y -> y >= Window.height))))
            .isEmpty();
    }

    private Stream<Integer> getXs() { return blocks.stream().map(Block::getX); }
    private Stream<Integer> getYs() { return blocks.stream().map(Block::getY); }

    public static int x = Window.width / 2;
    public static int y = 0;

    public static Piece random(Piece currentPiece) {
        Piece nextPiece = getNextPiece();
        return currentPiece != nextPiece ? nextPiece : random(nextPiece);
    }

    public static <T> Predicate<T> predicate(Predicate<T> t) { return (Predicate<T>) t; }

    public static Piece getNextPiece() {
        switch (new Random().nextInt(7)) {
            case 0: return pieceT.get();
            case 1: return pieceJ.get();
            case 2: return pieceL.get();
            case 3: return pieceO.get();
            case 4: return pieceS.get();
            case 5: return pieceZ.get();
            case 6: return pieceI.get();
            default: return getNextPiece();
        }
    }

    public static Supplier<Piece> pieceI = () -> Piece.def(Color.CYAN,
        x, y,
        x, y+32,
        x, y+64,
        x, y+96
    );

    public static Supplier<Piece> pieceO = () -> Piece.def(Color.YELLOW,
        x, y,
        x, y+32,
        x+32, y,
        x+32, y+32
    );

    public static Supplier<Piece> pieceJ = () -> Piece.def(Color.BLUE,
        x, y,
        x, y+32,
        x, y+64,
        x+32, y+64
    );

    public static Supplier<Piece> pieceL = () -> Piece.def(Color.ORANGE,
        x, y,
        x, y+32,
        x, y+64,
        x-32, y+64
    );

    public static Supplier<Piece> pieceS = () -> Piece.def(Color.LIMEGREEN,
        x, y,
        x, y+32,
        x+32, y+32,
        x+32, y+64
    );

    public static Supplier<Piece> pieceZ = () -> Piece.def(Color.RED,
        x, y,
        x, y+32,
        x+32, y+32,
        x+64, y+32
    );

    public static Supplier<Piece> pieceT = () -> Piece.def(Color.PURPLE,
        x-32, y,
        x, y+32,
        x+32, y,
        x, y
    );

}
