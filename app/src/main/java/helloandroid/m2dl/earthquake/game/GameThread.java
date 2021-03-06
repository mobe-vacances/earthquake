package helloandroid.m2dl.earthquake.game;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.Chronometer;

import helloandroid.m2dl.earthquake.MainActivity;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Canvas canvas;


    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (MainActivity.sharedPref.getBoolean("running",true)) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();


                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean isRunning) {
        SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
        editor.putBoolean("running", isRunning);
        editor.apply();
    }
}
