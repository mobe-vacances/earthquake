package helloandroid.m2dl.earthquake.entity.player;


import android.graphics.Point;

import helloandroid.m2dl.earthquake.game_controllers.Direction;

public class Player {

    /**
     * Current position of the user
     */
    private Point currentPosition;

    private Direction direction;


    private int step;

    private int height = 200;

    private int width = 200;

    public Player(Point initialPosition, Direction initialDirection, int initialStep) {
        currentPosition = initialPosition;
        step = initialStep;
        direction = initialDirection;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Point getPosition() {
        return currentPosition;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void updatePosition() {
        switch(direction) {
            case UP:
                currentPosition.y -= step;
                break;
            case DOWN:
                currentPosition.y += step;
                break;
            case LEFT:
                currentPosition.x -= step;
                break;
            case RIGHT:
                currentPosition.x += step;
                break;
        }
    }

    public int getStep() {
        return step;
    }
}
