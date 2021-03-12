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

    private double xSpeed = 0.0;
    private double ySpeed = 0.0;

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

        xSpeed = updatePlayerSpeed(xSpeed + xAcceleration*delta);
        ySpeed = updatePlayerSpeed(ySpeed + yAcceleration*delta);

        playerRect.offsetTo(
                (int)(playerRect.left + xSpeed*delta),
                (int)(playerRect.top + ySpeed*delta)
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

    private static double updatePlayerSpeed(double newSpeed) {
        return Math.max(
                -1*GameConstants.PLAYER_MAX_SPEED,
                Math.min(
                        newSpeed,
                        GameConstants.PLAYER_MAX_SPEED
                )
        );
    }

    public Rect getHitbox() {
        return playerRect;
    }
}
