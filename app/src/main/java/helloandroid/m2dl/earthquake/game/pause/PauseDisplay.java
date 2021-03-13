package helloandroid.m2dl.earthquake.game.pause;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;

public class PauseDisplay implements Drawable {

    private final Paint paint = new Paint();

    @Override
    public int getZIndex() {
        return GameConstants.LEVEL_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {

        paint.setColor(Color.WHITE);
        paint.setTextSize(GameConstants.PAUSE_TITLE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText(
                "PAUSE",
                DisplayScale.getRect().width()/2,
                DisplayScale.getRect().height()/2,
                paint
        );
    }

}
