package helloandroid.m2dl.coronattack.game.background;

import android.graphics.Canvas;
import android.graphics.Color;

import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.core.Updatable;

public class Background implements Drawable, Updatable {

    private double intensity = 0.0;

    private double targetIntensity = 0.0;

    @Override
    public int getZIndex() {
        return GameConstants.BACKGROUND_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(
            (int) (0.8*intensity*255),
            (int) (0.8*intensity*255),
            (int) (intensity*255)
        ));
    }

    @Override
    public void update(int delta) {
        if (intensity < targetIntensity) {
            intensity = Math.min(intensity + GameConstants.BACKGROUND_COLOR_INERTIA*delta, targetIntensity);
        } else {
            intensity = Math.max(intensity - GameConstants.BACKGROUND_COLOR_INERTIA*delta, targetIntensity);
        }
    }

    public void setTargetIntensity(double targetIntensity) {
        this.targetIntensity = targetIntensity;
    }
}
