package helloandroid.m2dl.earthquake.game.bonus;

import android.graphics.Rect;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.game.mobengine.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.score_and_level_handlers.AutoHandler;

public class BonusSpawn implements AutoHandler {

    private final List<Bonus> placedBonuses = new ArrayList<>();

    private Rect playerHitbox;

    private final Handler handler = new Handler();

    private final Runnable addBonus = () -> {
        if(placedBonuses.size() < GameConstants.BONUS_MAX_OCCURRENCES) {
            Bonus newBonus = new Bonus(playerHitbox, this);

            while (intersectsWithPlacedBonuses(newBonus)){
                newBonus = new Bonus(playerHitbox, this);
            }
            placedBonuses.add(newBonus);
            GameEngine.addGameElements(newBonus);
        }
        handler.postDelayed(this.addBonus, GameConstants.BONUS_MIN_DELAY + RandomService.get().nextInt(GameConstants.BONUS_MAX_DELAY_VARIATION));
    };

    public BonusSpawn(Rect playerHitbox) {
        this.playerHitbox = playerHitbox;
        handler.postDelayed(this.addBonus, GameConstants.BONUS_MIN_DELAY + RandomService.get().nextInt(GameConstants.BONUS_MAX_DELAY_VARIATION));
    }

    public void removeBonus(Bonus bonus) {
        placedBonuses.remove(bonus);
        GameEngine.removeGameElement(bonus);
    }

    private boolean intersectsWithPlacedBonuses(Bonus newBonus) {
        for(Bonus bonus : placedBonuses) {
            if(Rect.intersects(newBonus.getMaxHitbox(), bonus.getMaxHitbox())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeCallback() {
        handler.removeCallbacksAndMessages(null);
    }
}
