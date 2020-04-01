package sample.core.util;

import javafx.animation.AnimationTimer;

public class TimerUtil {

    public static AnimationTimer make(int interval, Runnable onNext) {
        return make(interval, onNext, () -> {}, () -> {});
    }

    public static AnimationTimer make(int interval, Runnable onNext, Runnable onStart) {
        return make(interval, onNext, onStart, () -> {});
    }

    public static AnimationTimer make(int interval, Runnable onNext, Runnable onStart, Runnable onStop) {
        return new AnimationTimer() {
            int frameCount = -1;
            @Override public void handle(long now) { if ((frameCount+= 1) % interval == 0) onNext.run(); }

            @Override public void start() { super.start(); onStart.run(); }

            @Override public void stop() { super.stop(); onStop.run(); }
        };
    }



}
