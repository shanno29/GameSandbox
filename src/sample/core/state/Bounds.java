package sample.core.state;

public class Bounds {
    public final Integer scale;
    public final Integer width;
    public final Integer height;
    public final Integer interval;
    public final String title;

    public static Bounds empty = new Bounds(0, 0, 0, 0, "");

    private Bounds(Integer height, Integer width, Integer scale, Integer interval, String title) {
        this.height = height;
        this.width = width;
        this.scale = scale;
        this.title = title;
        this.interval = interval;
    }

    public Bounds withScale(Integer scale) {
        return new Bounds(this.height, this.width, scale, this.interval, this.title);
    }

    public Bounds withWidth(Integer width) {
        return new Bounds(this.height, width, this.scale, this.interval, this.title);
    }

    public Bounds withHeight(Integer height) {
        return new Bounds(height, this.width, this.scale, this.interval, this.title);
    }

    public Bounds withTitle(String title) {
        return new Bounds(this.height, this.width, this.scale, this.interval, title);
    }

    public Bounds withInterval(Integer interval) {
        return new Bounds(this.height, this.width, this.scale, interval, this.title);
    }

}
