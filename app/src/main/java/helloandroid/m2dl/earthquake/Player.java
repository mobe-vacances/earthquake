package helloandroid.m2dl.earthquake;


public class Player {

    /**
     * Current position of the user
     */
    private android.graphics.Point currentPosition;

    private Direction direction;

    private int step;

    public Player(android.graphics.Point initialPosition, Direction initialDirection, int initialStep) {
        currentPosition = initialPosition;
        step = initialStep;
        direction = initialDirection;
    }

    public android.graphics.Point getPosition() {
        return currentPosition;
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

}
