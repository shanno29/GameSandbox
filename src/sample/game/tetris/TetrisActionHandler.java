package sample.game.tetris;

import sample.core.constant.Pov;
import sample.game.tetris.actions.*;
import sample.core.state.Bounds;
import sample.game.tetris.actions.Flags;
import sample.core.state.State;
import sample.core.state.Store;
import sample.game.tetris.entity.Piece;

import java.util.Optional;
import java.util.function.Supplier;

public class TetrisActionHandler {

    public static TetrisState initialState(Bounds bounds) {
        return TetrisState.empty
            .withPiece(Optional.empty())
            .withBounds(bounds)
            .withFlags(Flags.empty
                .withRelaxedBounds(true)
                .withInvertColors(false)
            );
    };

    public static Supplier<Store<TetrisState>> initStore(Bounds bounds) {
        return () -> Store.def(TetrisActionHandler.initialState(bounds), TetrisActionHandler.onAction);
    }

    public static State.StateMutator<TetrisState> onAction = (action) -> (state) -> {
        if (action instanceof Move) {
            return TetrisActionHandler.onMoveAction.apply(action).apply(state);
        }
        if (action instanceof Progress) {
            return TetrisActionHandler.onProgressAction.apply(action).apply(state);
        }
        if (action instanceof Rotate) {
            return TetrisActionHandler.onRotateAction.apply(action).apply(state);
        }

        if (action instanceof Toggle) {
            return TetrisActionHandler.onToggleAction.apply(action).apply(state);
        }

        return state;
    };

    public static State.StateMutator<TetrisState> onToggleAction = (action) -> (state) -> {
        if (!(action instanceof Toggle)) return state;
        System.out.println("[onToggleAction]");

        if (action instanceof Toggle.GameRules) {
            Toggle.GameRules rules = (Toggle.GameRules) action;
            return state.withFlags(state.flags.withRelaxedBounds(!state.flags.relaxBounds));
        }

        return state;
    };

    public static State.StateMutator<TetrisState> onProgressAction =  (action) -> (state) -> {
        if (!(action instanceof Progress)) return state;
        System.out.println("[onProgressAction]");

        if (action instanceof Progress.NewGame) {
            return state.withPiece(Optional.of(Piece.random(null, state.bounds)));
        }

        return state;
    };

    public static State.StateMutator<TetrisState> onRotateAction = (action) -> (state) -> {
        if (!(action instanceof Rotate)) return state;
        System.out.println("[onRotateAction]");
        // Optional<Rotate> rotate = Optional.of(action).flatMap(ifClass(Rotate.class));
        return state;
    };

    public static State.StateMutator<TetrisState> onMoveAction = (action) -> (state) -> {
        if (!(action instanceof Move)) return state;
        System.out.println("[onMoveAction]");
        Move move = (Move) action;

        if (move.getPov().equals(Pov.Up)) {
            move.getPiece().ifPresent(p -> p.shiftX(-move.getDist()));
            return state;
        }

        if (move.getPov().equals(Pov.Down)) {
            move.getPiece().ifPresent(p -> p.shiftX(move.getDist()));
            return state;
        }

        if (move.getPov().equals(Pov.Left)) {
            move.getPiece().ifPresent(p -> p.shiftY(-move.getDist()));
            return state;
        }

        if (move.getPov().equals(Pov.Right)) {
            move.getPiece().ifPresent(p -> p.shiftY(move.getDist()));
            return state;
        }
        return state;
    };

}
