package helloandroid.m2dl.earthquake;

import java.util.Random;

public class PointCustom {
    private int x;
    private int y;

    public PointCustom() {
        this.x = new Random().nextInt(500);
        this.y = new Random().nextInt(500);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
