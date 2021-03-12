package helloandroid.m2dl.earthquake.game.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;

public class Player implements Drawable, Updatable {

    private final Rect playerRect = new Rect(
        DisplayScale.getRect().centerX() - GameConstants.PLAYER_SIZE / 2,
        DisplayScale.getRect().centerY() - GameConstants.PLAYER_SIZE / 2,
        DisplayScale.getRect().centerX() + GameConstants.PLAYER_SIZE / 2,
        DisplayScale.getRect().centerY() + GameConstants.PLAYER_SIZE / 2
    );

    private double xAcceleration = 0.0;
    private double yAcceleration = 0.0;

    private int rotation = 0;

    @Override
    public int getZIndex() {
        return GameConstants.PLAYER_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix rotator = new Matrix();
        rotator.postRotate(rotation,playerRect.width()/2.0f,playerRect.height()/2.0f);
        rotator.postTranslate(playerRect.left, playerRect.top);
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(BitmapStore.getBitmap(R.drawable.world), playerRect.width(), playerRect.height(), false),
                rotator,
                null
        );
    }

    @Override
    public void update(int delta) {
        rotation = (int) ((rotation + GameConstants.PLAYER_ROTATION_SPEED*delta) % 360);

        double direction = Math.atan2(yAcceleration,xAcceleration);
        double speed = Math.min(Math.sqrt(xAcceleration*xAcceleration + yAcceleration*yAcceleration),GameConstants.PLAYER_MAX_SPEED);

        playerRect.offsetTo(
                (int)(playerRect.left + speed*Math.cos(direction)*delta),
                (int)(playerRect.top + speed*Math.sin(direction)*delta)
        );

        if(!GameState.getGameRect().contains(playerRect)) {
            GameState.gameOver();
        }
    }

    public void setxAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public Rect getHitbox() {
        return playerRect;
    }
}
