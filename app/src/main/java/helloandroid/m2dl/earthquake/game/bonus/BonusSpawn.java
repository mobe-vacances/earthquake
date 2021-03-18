package helloandroid.m2dl.earthquake.game.bonus;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.game.mobengine.auto_handlers.AutoHandler;
import helloandroid.m2dl.earthquake.game.mobengine.utils.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.core.GameEngine;
import helloandroid.m2dl.earthquake.game.geometry.Circle;

public class BonusSpawn extends AutoHandler {

    private final List<Bonus> placedBonuses = new ArrayList<>();

    private final Circle playerHitbox;


    public BonusSpawn(Circle playerHitbox) {
        this.playerHitbox = playerHitbox;
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
    protected int getTriggerDelay() {
        return RandomService.nextIntBetween(GameConstants.BONUS_MIN_DELAY, GameConstants.BONUS_MAX_DELAY);
    }

    @Override
    protected void onTrigger() {
        if(placedBonuses.size() < GameConstants.BONUS_MAX_OCCURRENCES) {
            Bonus newBonus = new Bonus(playerHitbox, this);

            while (intersectsWithPlacedBonuses(newBonus)){
                newBonus = new Bonus(playerHitbox, this);
            }
            placedBonuses.add(newBonus);
            GameEngine.addGameElements(newBonus);
        }
    }


}
