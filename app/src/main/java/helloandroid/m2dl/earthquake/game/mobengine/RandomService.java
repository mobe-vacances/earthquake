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

    public static int nextIntBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static double nextDoubleBetween(double min, double max) {
        return (max - min)*random.nextDouble() + min;
    }

    public static float nextFloatBetween(float min, float max) {
        return (max - min)*random.nextFloat() + min;
    }
}
