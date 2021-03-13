package helloandroid.m2dl.earthquake.game.onTouch;

import android.view.MotionEvent;
import android.view.View;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;
import helloandroid.m2dl.earthquake.game.pause.Pause;

public class OnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(Pause.intersectsWithPause((int) (event.getX() / DisplayScale.getScale()), (int) (event.getY() / DisplayScale.getScale()))){
            Pause.pauseGame();
        }
        else if (!GameEngine.isRunning()) {
            GameEngine.start();
        }
        else if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            BulletTime.turnOnBulletTime();
        }
        return true;
    }


}
