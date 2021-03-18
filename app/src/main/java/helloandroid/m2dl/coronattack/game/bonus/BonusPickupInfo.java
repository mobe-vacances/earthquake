package helloandroid.m2dl.coronattack.game.bonus;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.core.Updatable;

public class BonusPickupInfo implements Drawable, Updatable {

    private final Rect textBounds = new Rect();

    private final Paint paint = new Paint();

    private final String text;

    private final float x;

    private final float y;

    private int rotation = 0;

    private float textSize = GameConstants.BONUS_PICKUP_BASE_TEXT_SIZE;

    public BonusPickupInfo(int score, float x, float y) {
        this.text = "+" + score;
        this.x = x;
        this.y = y;

        paint.setColor(GameConstants.HEADER_BG_COLOR);
        paint.setTextSize(GameConstants.PAUSE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public int getZIndex() {
        return GameConstants.BONUS_PICKUP_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.getTextBounds(text,0, text.length(), textBounds);
        paint.setTextSize(textSize);
        canvas.rotate(rotation, x, y);
        canvas.drawText(text, x, y - textBounds.exactCenterY(), paint);
        canvas.rotate(-1*rotation, x, y);
    }

    @Override
    public void update(int delta) {
        rotation = (int) ((rotation + GameConstants.BONUS_PICKUP_ROTATION_SPEED *delta) % 360);

        textSize = (float) Math.max(textSize - GameConstants.BONUS_PICKUP_DEGROWTH_SPEED*delta,1);

        if (textSize == 1) {
            GameEngine.removeGameElement(this);
        }
    }
}
