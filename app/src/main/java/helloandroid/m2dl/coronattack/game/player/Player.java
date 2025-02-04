package helloandroid.m2dl.coronattack.game.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.VibrationEffect;

import java.util.function.Consumer;

import helloandroid.m2dl.coronattack.R;
import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.geometry.Circle;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.coronattack.game.mobengine.utils.RandomService;
import helloandroid.m2dl.coronattack.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.coronattack.game.particle.Particle;
import helloandroid.m2dl.coronattack.game.state.GameState;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.core.Updatable;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.BitmapStore;
import helloandroid.m2dl.coronattack.game.mobengine.utils.DisplayScale;

public class Player implements Drawable, Updatable {

    private final Circle playerCircle = new Circle(new Rect(
            DisplayScale.getRect().centerX() - GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerY() - GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerX() + GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerY() + GameConstants.PLAYER_SIZE / 2
    ));

    private float x = playerCircle.getEnclosingRect().left;
    private float y = playerCircle.getEnclosingRect().top;

    private double xAcceleration = 0.0;
    private double yAcceleration = 0.0;

    private float rotation = 0;

    private boolean dead = false;

    private final Consumer<Runnable> runOnUIThread;

    public Player(Consumer<Runnable> runOnUIThread) {
        this.runOnUIThread = runOnUIThread;
    }

    @Override
    public int getZIndex() {
        return GameConstants.PLAYER_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix rotator = new Matrix();
        rotator.postRotate(rotation,playerCircle.getEnclosingRect().width()/2.0f,playerCircle.getEnclosingRect().height()/2.0f);
        rotator.postTranslate(playerCircle.getEnclosingRect().left, playerCircle.getEnclosingRect().top);
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(BitmapStore.getBitmap(R.drawable.world), playerCircle.getEnclosingRect().width(), playerCircle.getEnclosingRect().height(), false),
                rotator,
                null
        );
        
    }

    @Override
    public void update(int delta) {
        rotation = (float) ((rotation + GameConstants.PLAYER_ROTATION_SPEED*delta) % 360);

        double direction = Math.atan2(yAcceleration,xAcceleration);
        double speed = Math.min(Math.sqrt(xAcceleration*xAcceleration + yAcceleration*yAcceleration),GameConstants.PLAYER_MAX_SPEED);

        x += speed*Math.cos(direction)*delta;
        y += speed*Math.sin(direction)*delta;

        playerCircle.getEnclosingRect().offsetTo(
                (int) x,
                (int) y
        );

        if(!GameState.getGameRect().contains(playerCircle.getEnclosingRect())) {
            die();
        }
    }

    public void setxAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public Circle getHitbox() {
        return playerCircle;
    }

    public void die() {
        if(!dead) {
            dead = true;

            SoundStore.stopLoopedSound(R.raw.game);
            SoundStore.playSound(R.raw.die, GameConstants.DEATH_SOUND_VOLUME);

            spawnPlayerDeathParticles(playerCircle.getEnclosingRect().exactCenterX(), playerCircle.getEnclosingRect().exactCenterY());
            GameEngine.removeGameElement(this);

            if(VibratorService.isVibrationsActive()){
                VibratorService.get().vibrate(VibrationEffect.createOneShot(GameConstants.GAME_OVER_DELAY/4, VibrationEffect.DEFAULT_AMPLITUDE));
            }

            runOnUIThread.accept(() -> new Handler().postDelayed(
                    GameState::gameOver,
                    GameConstants.GAME_OVER_DELAY
            ));
        }
    }

    private static void spawnPlayerDeathParticles(float x, float y) {
        if (GameState.getAnimationsActive()) {
            for (int i = GameConstants.PLAYER_DEATH_PARTICLE_NUMBER; i > 0; i--) {
                GameEngine.addGameElements(new Particle(
                        x,
                        y,
                        RandomService.nextFloatBetween(GameConstants.PLAYER_DEATH_PARTICLE_MIN_RADIUS, GameConstants.PLAYER_DEATH_PARTICLE_MAX_RADIUS),
                        RandomService.nextDoubleBetween(GameConstants.PLAYER_DEATH_PARTICLE_MIN_SPEED, GameConstants.PLAYER_DEATH_PARTICLE_MAX_SPEED),
                        2 * Math.PI * RandomService.get().nextDouble(),
                        GameConstants.PLAYER_DEATH_PARTICLE_COLORS[RandomService.get().nextInt(GameConstants.PLAYER_DEATH_PARTICLE_COLORS.length)],
                        false
                ));
            }
        }
    }

}
