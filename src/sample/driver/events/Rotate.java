package sample.driver.events;

import sample.entity.Piece;

public class Rotate extends Event {
    public Piece piece;
    private Rotate(Piece piece) { this.piece = piece; }
    public static Rotate def(Piece piece) { return new Rotate(piece); }
}
