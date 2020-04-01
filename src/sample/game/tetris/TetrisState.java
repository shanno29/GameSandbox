package sample.game.tetris;

import sample.core.state.Bounds;
import sample.game.tetris.actions.Flags;
import sample.core.state.State;
import sample.game.tetris.entity.Piece;

import java.util.List;
import java.util.Optional;

public class TetrisState extends State {

    public final List<Piece> pieces;
    public final Bounds bounds;
    public final Optional<Piece> piece;
    public final Flags flags;

    public static TetrisState empty = new TetrisState(
            Flags.empty,
            Bounds.empty,
            Optional.empty(),
            List.of()
    );

    private TetrisState(Flags flags, Bounds bounds, Optional<Piece> piece, List<Piece> pieces) {
        this.pieces = pieces;
        this.piece = piece;
        this.flags = flags;
        this.bounds = bounds;
    }

    public TetrisState withPiece(Optional<Piece> piece) {
        return new TetrisState(this.flags, this.bounds, piece, this.pieces);
    }

    public TetrisState withPieces(List<Piece> pieces) {
        return new TetrisState(this.flags, this.bounds, this.piece, pieces);
    }

    public TetrisState withFlags(Flags flags) {
        return new TetrisState(flags, this.bounds, this.piece, this.pieces);
    }

    public TetrisState withBounds(Bounds bounds) {
        return new TetrisState(this.flags, bounds, this.piece, this.pieces);
    }

}
