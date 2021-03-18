package helloandroid.m2dl.coronattack.game.pause;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.utils.DisplayScale;

public class PauseDisplay implements Drawable {

    private final Paint paint = new Paint();

    @Override
    public int getZIndex() {
        return GameConstants.LEVEL_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!GameEngine.isRunning()) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(GameConstants.PAUSE_TITLE_TEXT_SIZE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            canvas.drawText(
                    "PAUSE",
                    DisplayScale.getRect().width() / 2,
                    DisplayScale.getRect().height() / 2,
                    paint
            );
        }
    }

}
