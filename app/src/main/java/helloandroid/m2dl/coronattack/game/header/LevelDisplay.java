package helloandroid.m2dl.coronattack.game.header;

import android.graphics.Canvas;
import android.graphics.Paint;

import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.state.GameState;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.utils.DisplayScale;

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
                GameState.getLevel() + " : Level",
                DisplayScale.getRect().width() - GameConstants.HEADER_PADDING,
                (2*GameConstants.HEADER_HEIGHT/3),
                paint
        );
    }
}
