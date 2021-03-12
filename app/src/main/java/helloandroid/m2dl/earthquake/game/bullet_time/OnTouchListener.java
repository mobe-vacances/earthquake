package helloandroid.m2dl.earthquake.game.bullet_time;

import android.view.MotionEvent;
import android.view.View;

public class OnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            BulletTime.turnOnBulletTime();
        }
        return true;
    }
}
