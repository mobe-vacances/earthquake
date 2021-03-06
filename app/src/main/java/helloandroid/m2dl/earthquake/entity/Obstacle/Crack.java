package helloandroid.m2dl.earthquake.entity.Obstacle;

import android.graphics.Point;

import helloandroid.m2dl.earthquake.R;

enum StateCrack {
    BORN, INOFFENSIVE, DANGER, ENDING
}
public class Crack extends Point {

    private static final int END_LIFE = 30;
    private static final int INOFFENSIVE_LIFE = 15;
    private static final int BORN_LIFE = 5;

    private StateCrack state ;
    private int roundLife;
    private int img;

    public Crack (){
        state = StateCrack.INOFFENSIVE;
        roundLife = 1;
    }

    public int getImg() {
        return img;
    }

    public void addRoundLife(){
        roundLife += 1;
        if(roundLife<BORN_LIFE) {
            if(roundLife % 2 == 0) {
                img = 0;
            } else {
                img = R.drawable.grenade;
            }
            state = StateCrack.BORN;
        }else if(roundLife < INOFFENSIVE_LIFE){
            if(roundLife % 2 == 0) {
                img = 0;
            } else {
                img = R.drawable.grenade;
            }
            state = StateCrack.INOFFENSIVE;
        }else if(roundLife < END_LIFE){
            state = StateCrack.DANGER;
            img = R.drawable.grenade_danger;
        }else if(roundLife == END_LIFE){
            state = StateCrack.ENDING;
            img = R.drawable.grenade;
        }
    }

    boolean isInoffensive(){
        return state == StateCrack.INOFFENSIVE;
    }
    boolean isBorn(){
        return state == StateCrack.BORN;
    }
    boolean isEnding(){
        return state == StateCrack.ENDING;
    }
    boolean isDanger(){
        return state == StateCrack.DANGER;
    }
}
