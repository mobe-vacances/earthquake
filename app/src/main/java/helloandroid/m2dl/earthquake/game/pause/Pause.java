package helloandroid.m2dl.earthquake.game.pause;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.core.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.core.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.utils.DisplayScale;

public class Pause implements Drawable {

    private static final int LEFT = (DisplayScale.getRect().width() / 2) -  (DisplayScale.getRect().width()/ 7);
    private static final int TOP = 0;
    private static final int RIGHT = (DisplayScale.getRect().width() / 2) + (DisplayScale.getRect().width() / 7) ;
    private static final int BOTTOM = GameConstants.HEADER_HEIGHT;

    private final Paint paint = new Paint();
    private final Paint paintBackground = new Paint();
    private static Rect rect;

    @Override
    public int getZIndex() {
        return GameConstants.LEVEL_Z_INDEX;
    }

    public Pause(){
        rect = new Rect(
                LEFT,
                TOP,
                RIGHT,
                BOTTOM);
    }

    @Override
    public void draw(Canvas canvas) {

        paintBackground.setColor(GameConstants.HEADER_TEXT_COLOR);

        paint.setColor(Color.WHITE);
        paint.setTextSize(GameConstants.PAUSE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawRect(rect, paintBackground);
        canvas.drawText(
                "Pause",
                DisplayScale.getRect().width() / 2 ,
                (2 * GameConstants.HEADER_HEIGHT) / 3,
                paint
        );
    }


    public static boolean intersectsWithPause(int x, int y) {
        return rect.contains(x, y);
    }

    public static void pauseGame(){
        GameEngine.pause();
    }
}
