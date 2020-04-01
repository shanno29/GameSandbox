package sample.game.tetris.actions;

public class Flags {
    public final Boolean relaxBounds;
    public final Boolean invertColors;

    public static Flags empty = new Flags(false, false);

    private Flags(Boolean relaxBounds, Boolean invertColors) {
        this.relaxBounds = relaxBounds;
        this.invertColors = invertColors;
    }

    public Flags withRelaxedBounds(Boolean relaxBounds) {
        return new Flags(relaxBounds, this.invertColors);
    }

    public Flags withInvertColors(Boolean invertColors) {
        return new Flags(this.relaxBounds, invertColors);
    }

}
