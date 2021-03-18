package helloandroid.m2dl.coronattack.game.onTouch;

import android.view.MotionEvent;
import android.view.View;
import helloandroid.m2dl.coronattack.game.bullet_time.BulletTime;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.coronattack.game.mobengine.utils.DisplayScale;
import helloandroid.m2dl.coronattack.game.pause.Pause;

public class OnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            if (GameEngine.isRunning()) {
                if (Pause.intersectsWithPause((int) (event.getX() / DisplayScale.getScale()), (int) (event.getY() / DisplayScale.getScale()))) {
                    VibratorService.click();
                    Pause.pauseGame();
                } else {
                    BulletTime.turnOnBulletTime();
                }
            } else {
                GameEngine.start();
                VibratorService.click();
            }
        }
        return true;
    }
}
