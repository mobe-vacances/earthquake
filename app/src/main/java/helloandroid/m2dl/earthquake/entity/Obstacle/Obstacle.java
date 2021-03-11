package helloandroid.m2dl.earthquake.entity.Obstacle;

import android.graphics.Point;

enum StateObstacle {
    BORN, INOFFENSIVE, DANGER, ENDING
}
public class Obstacle extends Point {

    private static final int END_LIFE = 600;
    private static final int INOFFENSIVE_LIFE = 350;
    private static final int BORN_LIFE = 50;
    private static final int FRAME_WITHOUT_IMAGE = 50;

    private StateObstacle state ;
    private int roundLife;
    private boolean withoutImage;

    public Obstacle (){
        state = StateObstacle.INOFFENSIVE;
        roundLife = 1;
    }

    public void setState(StateObstacle s){
        state=s;
    }

    public boolean isWithoutImage() {
        return withoutImage;
    }

    public void addRoundLife(){
        if(state != StateObstacle.ENDING) {
            roundLife += 1;
            if (roundLife < BORN_LIFE) {
                if (roundLife % FRAME_WITHOUT_IMAGE == 0) {
                    withoutImage = true;
                } else {
                    withoutImage = false;
                }
                state = StateObstacle.BORN;
            } else if (roundLife < INOFFENSIVE_LIFE) {
                if (roundLife % FRAME_WITHOUT_IMAGE == 0) {
                    withoutImage = true;
                } else {
                    withoutImage = false;
                }
                state = StateObstacle.INOFFENSIVE;
            } else if (roundLife < END_LIFE) {
                state = StateObstacle.DANGER;
            } else if (roundLife == END_LIFE) {
                state = StateObstacle.ENDING;
            }
        }
    }

    boolean isInoffensive(){
        return state == StateObstacle.INOFFENSIVE;
    }
    boolean isBorn(){
        return state == StateObstacle.BORN;
    }
    boolean isEnding(){
        return state == StateObstacle.ENDING;
    }
    public boolean isDanger(){
        return state == StateObstacle.DANGER;
    }
}
