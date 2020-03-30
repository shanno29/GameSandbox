package sample.driver.events;

import sample.core.Window;
import sample.entity.Piece;

public class Move extends Event {
    public enum Pov { Up, Down, Left, Right, None}

    private Integer dist;
    private Piece piece;
    private Pov pov;

    private Move(Piece piece, Pov pov, Integer dist) { this.pov = pov; this.dist = dist; this.piece = piece; }
    public Move(Piece piece, Pov pov) { this(piece, pov, Window.scale); }

    public static Move def(Piece piece, Pov pov, Integer dist) { return new Move(piece, pov, dist); }
    public static Move def(Piece piece, Pov pov) { return new Move(piece, pov); }

    public Piece getPiece() { return piece; }
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
