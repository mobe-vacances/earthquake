package helloandroid.m2dl.earthquake.game.mobengine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import helloandroid.m2dl.earthquake.game.mobengine.elements.Updatable;


public class GameStep implements Runnable {

    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private final GameEngine gameEngine;
    private Canvas canvas;

    private long lastTimeStamp = System.currentTimeMillis();

    public GameStep(SurfaceHolder surfaceHolder, GameView gameView, GameEngine gameEngine) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run() {
        if(!GameEngine.isRunning()) {
            return;
        }

        long currentTimeStamp = System.currentTimeMillis();
        int delta = (int) (currentTimeStamp - lastTimeStamp);

        gameEngine.addElementsToAdd();
        for(Updatable updatable : gameEngine.getUpdatables() ) {
            updatable.update(delta);
        }
        gameEngine.removeElementsToRemove();

        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                this.gameView.draw(canvas);
            }
        } catch (Exception ignored) {
        } finally {
            if (canvas != null) {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        lastTimeStamp = currentTimeStamp;
    }
}
