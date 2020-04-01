package sample.game.tetris;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.core.constant.Pov;
import sample.core.base.Driver;
import sample.core.state.Bounds;
import sample.game.tetris.actions.*;
import sample.core.state.Store;
import sample.game.tetris.entity.Piece;

import java.util.ArrayList;
import java.util.stream.Stream;

import static sample.core.util.KeyboardUtil.*;
import static sample.core.util.FuncUtil.*;

public class TetrisDriver extends Driver {
    public final Store<TetrisState> store;

    public TetrisDriver(Main parent) {
        super(parent);
        store = TetrisActionHandler.initStore(getBounds()).get();
    }

    private Bounds _bounds;

    @Override
    public Bounds getBounds() {
        if (_bounds != null) { return _bounds; }
        else { return _bounds = Bounds.empty
            .withTitle("Tetris")
            .withHeight(32* 30)
            .withWidth(32* 20)
            .withInterval(4)
            .withScale(32);
        }
    }

    @Override
    public void loop() {
        store.getState(state -> {
            draw();
            if (state.piece.isEmpty()) {
                store.apply(new Progress.NewGame());
            }
            else {
                if (!state.flags.relaxBounds) store.apply(Move.def(state.piece, Pov.Down, _bounds.scale));
            }


            //
        });

//        store.getState(state -> {
//            if (!state.flags.relaxBounds)
//                store.apply(Move.def(state.piece, Pov.Down, state.bounds.scale));
//
////        if (piece.touching(oldPieces)) {
////            System.out.println("Touching Other Piece");
////            oldPieces.add(piece);
////            piece = null;
////        }
//
//            if (state.piece.touching(Side.TOP, getBounds())) {
//                store.apply(Move.def(state.piece, Pov.Down, getBounds().scale));
//            }
//
//            if (state.piece.touching(Side.LEFT, getBounds())) {
//                store.apply(Move.def(state.piece, Pov.Right, getBounds().scale));
//            }
//
//            if (state.piece.touching(Side.RIGHT, getBounds())) {
//                store.apply(Move.def(state.piece, Pov.Left, getBounds().scale));
//            }
//
//            if (state.piece.touching(Side.BOTTOM, getBounds())) {
//                store.apply(Move.def(state.piece, Pov.Up, getBounds().scale));
//
//                // oldPieces.add(piece);
//                // piece = Piece.random(piece);
//            }
//
//        });
    }

    @Override
    public void onKeyboardInput(KeyEvent event) {
        store.getState(state -> {
            KeyCode code = event.getCode();
            if (isQuit(code)) store.apply(new Progress.GameOver());
            else if (isDebug(code)) store.apply(new Toggle.GameRules());
            else if (isRotate(code)) store.apply(Rotate.def(state.piece));
            else if (isUp(code)) store.apply(Move.def(state.piece, Pov.Up, getBounds().scale));
            else if (isDown(code)) store.apply(Move.def(state.piece, Pov.Down, getBounds().scale));
            else if (isLeft(code)) store.apply(Move.def(state.piece, Pov.Left, getBounds().scale));
            else if (isRight(code)) store.apply(Move.def(state.piece, Pov.Right, getBounds().scale));
        });
    }

    @Override
    public void onMouseInput(MouseEvent event) { }

    public void draw() {
        store.getState(state ->
                Stream.of(
                        makeBackground(getBounds()),
                        state.piece.map(Piece::getRectangles).orElse(new ArrayList<Rectangle>().stream()),
                        state.pieces.stream().flatMap(Piece::getRectangles),
                        makeGrid(getBounds())
                )
                        .flatMap(__ -> __)
                        .forEach(this::addView)
        );
    }

    public static Stream<Rectangle> makeBackground(Bounds bounds) {
        return Stream.of(new Rectangle()).peek(rect -> {
            rect.setFill(Color.GREEN);
            rect.setHeight(bounds.height);
            rect.setWidth(bounds.width);
            rect.setLayoutX(0);
            rect.setLayoutY(0);
        });
    }

    public static Stream<Line> makeGrid(Bounds bounds) {
        return Stream.concat(
                Stream.iterate(0, lessThan(bounds.height), inc(bounds.scale))
                        .map(i -> new Line(0, i, bounds.width, i)),
                Stream.iterate(0, lessThan(bounds.width), inc(bounds.scale))
                        .map(i -> new Line(i, 0, i, bounds.height))
        );
    }

}
