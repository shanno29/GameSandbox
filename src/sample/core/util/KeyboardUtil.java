package sample.core.util;

import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public class KeyboardUtil {
    public static boolean isRight(KeyCode code) { return D.equals(code) || RIGHT.equals(code); }
    public static boolean isLeft(KeyCode code) { return A.equals(code) || LEFT.equals(code); }
    public static boolean isDown(KeyCode code) { return S.equals(code) || DOWN.equals(code); }
    public static boolean isUp(KeyCode code) { return W.equals(code) || UP.equals(code); }

    public static boolean isQuit(KeyCode code) { return Q.equals(code) || ESCAPE.equals(code); }
    public static boolean isDebug(KeyCode code) { return R.equals(code); }
    public static boolean isRotate(KeyCode code) { return E.equals(code); }
}
