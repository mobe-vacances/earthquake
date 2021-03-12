package helloandroid.m2dl.earthquake.game.mobengine;

import java.util.Random;

public class RandomService {

    private static final Random random = new Random();

    public static Random get() {
        return random;
    }

    public static double nextRelativeDouble() {
        return random.nextDouble() - 0.5;
    }
}
