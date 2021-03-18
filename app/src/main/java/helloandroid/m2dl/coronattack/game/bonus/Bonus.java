package helloandroid.m2dl.coronattack.game.bonus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import helloandroid.m2dl.coronattack.R;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.coronattack.game.mobengine.utils.RandomService;
import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.geometry.Circle;
import helloandroid.m2dl.coronattack.game.state.GameState;
import helloandroid.m2dl.coronattack.game.mobengine.core.Drawable;
import helloandroid.m2dl.coronattack.game.mobengine.core.Updatable;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.BitmapStore;

public class Bonus implements Drawable, Updatable {

    private final Rect rect = new Rect();

    private final Circle playerHitbox;

    private double size = 1;

    private BonusState state = BonusState.GROWING;

    private BonusSpawn bonusSpawn;

    public Bonus(Circle playerHitbox, BonusSpawn bonusSpawn) {
        this.playerHitbox = playerHitbox;
        this.bonusSpawn = bonusSpawn;

        rect.left = RandomService.get().nextInt(GameState.getGameRect().width() - GameConstants.BONUS_SIZE);
        rect.top = GameConstants.HEADER_HEIGHT + RandomService.get().nextInt(GameState.getGameRect().height() - GameConstants.BONUS_SIZE);

        rect.right = rect.left + GameConstants.BONUS_SIZE;
        rect.bottom = rect.top + GameConstants.BONUS_SIZE;
    }

    @Override
    public int getZIndex() {
        return GameConstants.BONUS_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(BitmapStore.getBitmap(R.drawable.mask), rect.width(), rect.height(), false),
                rect.left, rect.top,
                null
        );
    }

    @Override
    public void update(int delta) {
        if( Circle.intersects(playerHitbox, rect) ) {
            SoundStore.playSound(R.raw.coin, GameConstants.BONUS_PICKUP_SOUND_VOLUME);
            GameState.increaseScore(GameConstants.BONUS_BASE_SCORE *GameState.getLevel());
            bonusSpawn.removeBonus(this);
            if(GameState.getAnimationsActive()) {
                GameEngine.addGameElements(new BonusPickupInfo(GameConstants.BONUS_BASE_SCORE * GameState.getLevel(), rect.exactCenterX(), rect.exactCenterY()));
            }
            return;
        }

        if(state == BonusState.GROWING) {
            size = Math.min(size + GameConstants.BONUS_GROWING_SPEED*delta, GameConstants.BONUS_SIZE);

            state = size == GameConstants.BONUS_SIZE? BonusState.DEGROWING : BonusState.GROWING;
        } else if(state == BonusState.DEGROWING) {
            size = (int) Math.max(size - GameConstants.BONUS_GROWING_SPEED*delta, 1);

            if (size == 1) {
                bonusSpawn.removeBonus(this);
            }
        }

        rect.inset((int)(rect.width() - size)/2, (int)(rect.height() - size)/2);
    }

    public Rect getMaxHitbox() {
        return new Rect(
                (int)(rect.exactCenterX() - GameConstants.BONUS_SIZE / 2),
                (int)(rect.exactCenterY() + GameConstants.BONUS_SIZE / 2),
                (int)(rect.exactCenterX() - GameConstants.BONUS_SIZE / 2),
                (int)(rect.exactCenterY() + GameConstants.BONUS_SIZE / 2)
        );
    }

}
