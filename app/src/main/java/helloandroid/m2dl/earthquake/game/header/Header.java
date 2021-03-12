package helloandroid.m2dl.earthquake.game.header;

import android.graphics.Canvas;
import android.graphics.Paint;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;

public class Header implements Drawable {

    private final Paint headerPaint = new Paint();

    @Override
    public int getZIndex() {
        return GameConstants.HEADER_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        headerPaint.setColor(GameConstants.HEADER_BG_COLOR);
        canvas.drawRect(
                0,
                0,
                DisplayScale.getRect().right,
                GameConstants.HEADER_HEIGHT,
                headerPaint
        );
    }
}
