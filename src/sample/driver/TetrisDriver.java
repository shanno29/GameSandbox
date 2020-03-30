package sample.driver;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.core.Side;
import sample.core.Window;
import sample.driver.events.*;
import sample.entity.Block;
import sample.entity.Piece;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static sample.core.Controls.*;
import static sample.driver.events.Move.*;

public class TetrisDriver extends Driver {

    List<Piece> oldPieces = new ArrayList<>();
    Boolean relaxBounds = true;
    Piece piece = null;

    public TetrisDriver(Stage stage) {
        super(stage, 4, Window.title);
        addView(makeBackground());
    }

    public void setupPiece(Piece p) {
        piece = Piece.random(p);
        draw();
        onGameEvent(() -> new Move(piece, Pov.Down));

    }

    @Override
    public void loop() {

        // draw
        if (piece == null) { setupPiece(piece); }
        else {
            if (!relaxBounds) onGameEvent(() -> Move.def(piece, Pov.Down));
        }
        if (piece != null && piece.touching(oldPieces)) {
            System.out.println("Touching Other Piece");
            oldPieces.add(piece);
            piece = null;
        }

        if (piece != null && piece.touching(Side.Top)) {
            System.out.println("Touching Top");
            onGameEvent(() -> Move.def(piece, Pov.Down));
        }

        if (piece != null && piece.touching(Side.Left)) {
            System.out.println("Touching Left");
            onGameEvent(() -> Move.def(piece, Pov.Right));
        }

        if (piece != null && piece.touching(Side.Right)) {
            System.out.println("Touching Right");
            onGameEvent(() -> Move.def(piece, Pov.Left));
        }

        if (piece != null && piece.touching(Side.Bottom)) {
            System.out.println("Touching Bottom");
            onGameEvent(() -> Move.def(piece, Pov.Up));
            oldPieces.add(piece);
            piece = null;
        }

    }

    public void draw() {
        Stream.concat(
            piece.blocks.stream().map(Block::getRectangle),
            makeGrid().stream()
        ).forEach(this::addView);
    }

    public void onGameEvent(Supplier<Event> req) {
        Optional<Event> event = Optional.of(req.get())
                .filter(__ -> Objects.nonNull(this.piece));

        event
            .filter(x -> x instanceof Move).map(Move.class::cast)
            .ifPresent(this::onMoveEvent);

        event
            .filter(x -> x instanceof Progress)
            .ifPresent(this::onProgressEvent);

        event
            .filter(x -> x instanceof Rotate).map(Rotate.class::cast)
            .ifPresent(this::onRotateEvent);

        event
            .filter(x -> x instanceof Toggle).map(Toggle.class::cast)
            .ifPresent(this::onToggleEvent);
    }

    private void onToggleEvent(Event event) {
        Optional<Toggle> progress = Optional.of(event).map(Toggle.class::cast);;
        progress
                .filter(x -> x instanceof Toggle.GameRules).map(Toggle.GameRules.class::cast)
                .ifPresent(__ ->  this.relaxBounds = !this.relaxBounds);
    }

    private void onProgressEvent(Event event) {
        Optional<Progress> progress = Optional.of(event).map(Progress.class::cast);
        progress.filter(x -> x instanceof Progress.GameOver).map(Progress.GameOver.class::cast)
                .ifPresent(__ -> Platform.exit());
    }

    private void onRotateEvent(Event event) {
        Optional<Rotate> rotate = Optional.of(event).map(Rotate.class::cast);
    }

    private void onMoveEvent(Event event) {
        Optional<Move> move = Optional.of(event).map(Move.class::cast);
        move.filter(not(x -> x.getPiece().touching(oldPieces)));

        Integer distance = move.map(Move::getDist).orElse(0);

        move
            .filter(x -> Pov.Up.equals(x.getPov()))
            .map(Move::getPiece).ifPresent(Piece.shiftY(-distance));

        move
            .filter(x -> Pov.Down.equals(x.getPov()))
            .map(Move::getPiece).ifPresent(Piece.shiftY(+distance));

        move
            .filter(x -> Pov.Left.equals(x.getPov()))
            .map(Move::getPiece).ifPresent(Piece.shiftX(-distance));

        move
            .filter(x -> Pov.Right.equals(x.getPov()))
            .map(Move::getPiece).ifPresent(Piece.shiftX(+distance));
    }

    @Override
    public void onKeyboardEvent(KeyEvent event) {
        KeyCode code = event.getCode();
        if (isQuit(code)) onGameEvent(Progress.GameOver::new);
        else if (isDebug(code)) onGameEvent(Toggle.GameRules::new);
        else if (isRotate(code)) onGameEvent(() -> Rotate.def(piece));
        else if (isUp(code)) onGameEvent(() -> Move.def(piece, Pov.Up));
        else if (isDown(code)) onGameEvent(() -> Move.def(piece, Pov.Down));
        else if (isLeft(code)) onGameEvent(() -> Move.def(piece, Pov.Left));
        else if (isRight(code)) onGameEvent(() -> Move.def(piece, Pov.Right));
    }

    public static Rectangle makeBackground() {
        Rectangle background = new Rectangle(0,0, Color.WHITE);
        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setWidth(Window.width);
        background.setHeight(Window.height);
        return background;
    }

    public static List<Line> makeGrid() {
        return Stream.concat(
                Stream
                    .iterate(0, isLessThan(Window.width), increment(Window.scale))
                    .map((i) -> new Line(i, 0, i, Window.height)),
                Stream
                    .iterate(0, isLessThan(Window.height), increment(Window.scale))
                    .map((i) -> new Line(0, i, Window.width, i))
            )
            .collect(Collectors.toList());
    }

    public static UnaryOperator<Integer> increment(int d) { return (i) -> i + d; }
    public static Predicate<Integer> isGreaterThan(int d) { return (i) -> i < d; }
    public static Predicate<Integer> isLessThan(int d) { return (i) -> i < d; }
}

