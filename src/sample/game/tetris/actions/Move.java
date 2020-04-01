package sample.game.tetris.actions;

import sample.core.constant.Pov;
import sample.core.state.Action;
import sample.game.tetris.entity.Piece;

import java.util.Optional;

public class Move extends Action {

    private Integer dist;
    private Optional<Piece> piece;
    private Pov pov;

    public static Move def(Optional<Piece> piece, Pov pov, Integer dist) { return new Move(piece, pov, dist); }

    private Move(Optional<Piece> piece, Pov pov, Integer dist) { this.pov = pov; this.dist = dist; this.piece = piece; }

    public Optional<Piece> getPiece() { return piece; }
    public Integer getDist() { return dist; }
    public Pov getPov() { return pov; }

    @Override
    public String toString() {
        return "Move{" +
                "dist=" + dist +
                ", piece=" + piece +
                ", pov=" + pov +
                '}';
    }
}
