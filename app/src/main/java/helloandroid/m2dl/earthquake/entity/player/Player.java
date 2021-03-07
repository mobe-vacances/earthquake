package helloandroid.m2dl.earthquake.entity.player;


import android.graphics.Point;

public class Player {

    /**
     * X coordinate of the user's position
     */
    private float positionX;

    /**
     * Y coordinate of the user's position
     */
    private float positionY;

    /**
     * X coordinate of the user's direction
     */
    public float directionX;

    /**
     * Y coordinate of the user's direction
     */
    public float directionY;

    /**
     * Multiplier allowing the game to accelerate or slow down the player
     * depending on external events
     */
    public float multiplier;

    private final int height = 50;

    private final int width = 50;

    public Player(Point initialPosition) {
        positionX = initialPosition.x;
        positionY = initialPosition.y;
        directionX = 0;
        directionY = 0;
        multiplier = 1;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Point getPosition() {
        return new Point((int) positionX, (int) positionY);
    }

    public void updatePosition() {
        positionX += directionX;
        positionY += directionY;
    }

}
