package helloandroid.m2dl.earthquake.game.header;

import android.graphics.Canvas;
import android.graphics.Paint;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;

public class LevelDisplay implements Drawable {

    private final Paint paint = new Paint();

    @Override
    public int getZIndex() {
        return GameConstants.LEVEL_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(GameConstants.HEADER_TEXT_COLOR);
        paint.setTextSize(GameConstants.HEADER_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText(
                "" + GameState.getLevel(),
                DisplayScale.getRect().width() - GameConstants.HEADER_PADDING,
                GameConstants.HEADER_TEXT_SIZE,
                paint
        );
    }
}
