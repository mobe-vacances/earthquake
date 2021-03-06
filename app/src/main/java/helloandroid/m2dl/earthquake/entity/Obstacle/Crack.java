package helloandroid.m2dl.earthquake.entity.Obstacle;

import android.graphics.Point;

import helloandroid.m2dl.earthquake.R;

enum StateCrack {
    BORN, INOFFENSIVE, DANGER, ENDING
}
public class Crack extends Point {

    private static final int END_LIFE = 600;
    private static final int INOFFENSIVE_LIFE = 350;
    private static final int BORN_LIFE = 50;
    private static final int FRAME_WITHOUT_IMAGE = 50;

    private StateCrack state ;
    private int roundLife;
    private boolean withoutImage;

    public Crack (){
        state = StateCrack.INOFFENSIVE;
        roundLife = 1;
    }

    public boolean isWithoutImage() {
        return withoutImage;
    }

    public void addRoundLife(){
        roundLife += 1;
        if(roundLife<BORN_LIFE) {
            if(roundLife % FRAME_WITHOUT_IMAGE == 0) {
                withoutImage = true;
            } else {
                withoutImage = false;
            }
            state = StateCrack.BORN;
        }else if(roundLife < INOFFENSIVE_LIFE){
            if(roundLife % FRAME_WITHOUT_IMAGE == 0) {
                withoutImage = true;
            } else {
                withoutImage = false;
            }
            state = StateCrack.INOFFENSIVE;
        }else if(roundLife < END_LIFE){
            state = StateCrack.DANGER;
        }else if(roundLife == END_LIFE){
            state = StateCrack.ENDING;
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
    public boolean isDanger(){
        return state == StateCrack.DANGER;
    }
}
