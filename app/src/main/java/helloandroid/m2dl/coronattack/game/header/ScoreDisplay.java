package helloandroid.m2dl.coronattack.game.header;

import android.graphics.Canvas;
import android.graphics.Paint;

import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.state.GameState;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;

public class ScoreDisplay implements Drawable {

    private final Paint paint = new Paint();

    @Override
    public int getZIndex() {
        return GameConstants.SCORE_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(GameConstants.HEADER_TEXT_COLOR);
        paint.setTextSize(GameConstants.HEADER_TEXT_SIZE);

        canvas.drawText(
                "Score : " + GameState.getScore(),
                GameConstants.HEADER_PADDING,
                (2*GameConstants.HEADER_HEIGHT/3),
                paint
        );
    }
}
