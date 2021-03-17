package helloandroid.m2dl.earthquake.game.state;

import android.graphics.Rect;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.utils.DisplayScale;

public class GameState {

    private static int score = 0;

    private static int level = 1;

    private static Rect gameRect = new Rect(
            0,
            GameConstants.HEADER_HEIGHT,
            DisplayScale.getRect().right,
            DisplayScale.getRect().bottom
    );

    private static Runnable gameOverRunnable;

    public static void gameOver() {
        gameOverRunnable.run();
    }

    public static int getLevel() {
        return level;
    }

    public static int getScore() {
        return score;
    }

    public static void incrementLevel() {
        level++;
    }

    public static void increaseScore(int value) {
        score += value;
    }

    public static Rect getGameRect() {
        return gameRect;
    }

    public static void resetGameState(Runnable gameOverRunnable) {
        GameState.gameOverRunnable = gameOverRunnable;
        score = 0;
        level = 1;
        gameRect = new Rect(
            0,
            GameConstants.HEADER_HEIGHT,
            DisplayScale.getRect().right,
            DisplayScale.getRect().bottom
        );
    }
}
