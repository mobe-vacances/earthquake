package helloandroid.m2dl.earthquake.game.bullet_time;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.core.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.core.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.utils.DisplayScale;

public class BulletTime implements Drawable,Updatable {

    private static double bulletTimeMultiplier = 1;

    private static BulletTimeState state = BulletTimeState.READY;

    private static int msCount = 0;

    private final Paint paint = new Paint();

    public static double getBulletTimeMultiplier() {
        return bulletTimeMultiplier;
    }

    public static void turnOnBulletTime() {
        if(state == BulletTimeState.READY) {
            bulletTimeMultiplier = GameConstants.BULLET_TIME_MULTIPLIER;
            state = BulletTimeState.ACTIVE;
        }
    }

    public BulletTime() {
        msCount = 0;
        state = BulletTimeState.READY;
        bulletTimeMultiplier = 1;
    }

    @Override
    public int getZIndex() {
        return GameConstants.BULLET_TIME_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {

        double charge = 1.0;

        if(state == BulletTimeState.ACTIVE) {
            charge = (double)(GameConstants.BULLET_TIME_DURATION - msCount) / (double)GameConstants.BULLET_TIME_DURATION;
        } else if (state == BulletTimeState.CHARGING) {
            charge = (double)msCount / (double)GameConstants.BULLET_TIME_COOLDOWN;
        }

        paint.setColor(Color.rgb((int)((1-charge)*255), (int)(charge*255),0));

        canvas.drawRect(
                0,
                0,
                (float) (charge*DisplayScale.getRect().right),
                GameConstants.BULLET_TIME_HEIGHT,
                paint
        );
    }

    @Override
    public void update(int delta) {
        if(state == BulletTimeState.READY) {
            return;
        }

        msCount += delta;

        if(state == BulletTimeState.ACTIVE && msCount >= GameConstants.BULLET_TIME_DURATION) {
            bulletTimeMultiplier = 1;
            msCount = 0;
            state = BulletTimeState.CHARGING;
        } else if(state == BulletTimeState.CHARGING && msCount >= GameConstants.BULLET_TIME_COOLDOWN) {
            bulletTimeMultiplier = 1;
            msCount = 0;
            state = BulletTimeState.READY;
        }
    }
}
