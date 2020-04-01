package sample.game.tetris.actions;

import sample.core.state.Action;

public class Progress extends Action {
    public static class NewGame extends Progress { }
    public static class NewPiece extends Progress { }
    public static class GameOver extends Progress { }
}
