package helloandroid.m2dl.earthquake.game;

import android.graphics.Color;

public class GameConstants {

    public static final int PLAYER_SIZE = 50;
    public static final double PLAYER_MAX_SPEED = 0.35;
    public static final double PLAYER_ROTATION_SPEED = 0.2;

    public static final double ACCELEROMETER_SENSIBILITY = 0.0008;

    public static final double BACKGROUND_COLOR_INERTIA = 0.0007;

    public static final int HEADER_BG_COLOR = Color.rgb(134,218,241);
    public static final int HEADER_HEIGHT = 100;
    public static final int HEADER_PADDING = 40;

    public static final int HEADER_TEXT_SIZE = 80;
    public static final int HEADER_TEXT_COLOR = Color.rgb(72,118,130);

    public static final int ENEMY_SIZE = 40;
    public static final double ENEMY_INITIAL_SPEED = 0.0002;
    public static final double ENEMY_ROTATION_SPEED = 0.5;
    public static final double ENEMY_GROWING_SPEED = 0.05;
    public static final int ENEMY_GROWING_TIME = 3000;
    public static final int ENEMY_BASE_SPAWN_RATE = 3000;
    public static final int ENEMY_SPAWN_RATE_VARIATION = 3000;

    public static final int BONUS_SIZE = 50;
    public static final double BONUS_GROWING_SPEED = 0.005;
    public static final int BONUS_SCORE = 50;
    public static final int BONUS_MIN_DELAY = 1000;
    public static final int BONUS_MAX_DELAY_VARIATION = 2000;
    public static final int BONUS_MAX_OCCURRENCES = 10;

    public static final double BULLET_TIME_MULTIPLIER = 0.2;
    public static final int BULLET_TIME_DURATION = 3000;
    public static final int BULLET_TIME_COOLDOWN = 5000;
    public static final int BULLET_TIME_HEIGHT = 10;

    public static final int SCORE_AUTO_INCREMENT_TIME = 1000;
    public static final int SCORE_AUTO_INCREMENT_VALUE = 1;

    public static final int LEVEL_AUTO_INCREMENT_TIME = 20000;

    public static final int BACKGROUND_Z_INDEX = 0;
    public static final int BONUS_Z_INDEX = 10;
    public static final int ENEMY_Z_INDEX = 20;
    public static final int PLAYER_Z_INDEX = 100;
    public static final int HEADER_Z_INDEX = 150;
    public static final int SCORE_Z_INDEX = 151;
    public static final int LEVEL_Z_INDEX = 152;
    public static final int BULLET_TIME_Z_INDEX = 160;
}
