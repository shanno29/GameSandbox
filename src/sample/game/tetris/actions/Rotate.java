package sample.game.tetris.actions;

import sample.core.state.Action;
import sample.game.tetris.entity.Piece;

import java.util.Optional;

public class Rotate extends Action {
    public Optional<Piece> piece;
    private Rotate(Optional<Piece> piece) { this.piece = piece; }
    public static Rotate def(Optional<Piece> piece) { return new Rotate(piece); }
}
