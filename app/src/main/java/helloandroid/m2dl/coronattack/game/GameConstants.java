package helloandroid.m2dl.coronattack.game;

import android.graphics.Color;

import helloandroid.m2dl.coronattack.R;

public class GameConstants {

    public static final String APP_SHARED_PREFERENCES_KEY = "localScore";

    public static final int[] USED_BITMAP_IDS = {
            R.drawable.world,
            R.drawable.coronavirus_safe,
            R.drawable.coronavirus,
            R.drawable.mask
    };

    public static final int[] USED_SOUNDS_IDS = {
            R.raw.game,
            R.raw.die,
            R.raw.coin,
            R.raw.click,
            R.raw.start,
            R.raw.menu
    };

    public static final int PLAYER_SIZE = 50;
    public static final double PLAYER_MAX_SPEED = 0.35;
    public static final double PLAYER_ROTATION_SPEED = 0.2;

    public static final double PLAYER_DEATH_PARTICLE_MIN_SPEED = 0.05;
    public static final double PLAYER_DEATH_PARTICLE_MAX_SPEED = 0.5;
    public static final int PLAYER_DEATH_PARTICLE_NUMBER = 500;
    public static final float PLAYER_DEATH_PARTICLE_MIN_RADIUS = 2.0f;
    public static final float PLAYER_DEATH_PARTICLE_MAX_RADIUS = 15.0f;
    public static final int[] PLAYER_DEATH_PARTICLE_COLORS = {
            Color.rgb(87,47,0),
            Color.rgb(3,170,111),
            Color.rgb(134,218,241)
    };

    public static final double ACCELEROMETER_SENSIBILITY = 0.7;

    public static final double BACKGROUND_COLOR_INERTIA = 0.0007;

    public static final int HEADER_BG_COLOR = Color.rgb(134,218,241);
    public static final int HEADER_HEIGHT = 100;
    public static final int HEADER_PADDING = 40;

    public static final int PAUSE_TEXT_SIZE  = 30;
    public static final int PAUSE_TITLE_TEXT_SIZE  = 100;

    public static final int HEADER_TEXT_SIZE = 40;
    public static final int HEADER_TEXT_COLOR = Color.rgb(72,118,130);

    public static final int ENEMY_SIZE = 40;
    public static final double ENEMY_INITIAL_SPEED = 0.0002;
    public static final double ENEMY_ROTATION_SPEED = 0.5;
    public static final double ENEMY_GROWING_SPEED = 0.025;
    public static final int ENEMY_BASE_SPAWN_RATE_MIN = 3000;
    public static final int ENEMY_BASE_SPAWN_RATE_MAX = 6000;
    public static final float ENEMY_MIN_ROTATION_SPEED = -0.6f;
    public static final float ENEMY_MAX_ROTATION_SPEED = 0.6f;

    public static final double ENEMY_DEATH_PARTICLE_MIN_SPEED = 0.1;
    public static final double ENEMY_DEATH_PARTICLE_MAX_SPEED = 0.3;
    public static final int ENEMY_DEATH_PARTICLE_MIN_NUMBER = 10;
    public static final int ENEMY_DEATH_PARTICLE_MAX_NUMBER = 20;
    public static final float ENEMY_DEATH_PARTICLE_RADIUS = 3.0f;
    public static final int[] ENEMY_DEATH_PARTICLE_COLORS = {
            Color.rgb(238,97,97),
            Color.rgb(249,177,177),
            Color.rgb(249,149,149),
            Color.rgb(243,124,124)
    };

    public static final int BONUS_SIZE = 50;
    public static final double BONUS_GROWING_SPEED = 0.005;
    public static final int BONUS_BASE_SCORE = 10;
    public static final int BONUS_MIN_DELAY = 1000;
    public static final int BONUS_MAX_DELAY = 3000;
    public static final int BONUS_MAX_OCCURRENCES = 10;

    public static final double BONUS_PICKUP_ROTATION_SPEED = 0.15;
    public static final int BONUS_PICKUP_BASE_TEXT_SIZE = 40;
    public static final double BONUS_PICKUP_DEGROWTH_SPEED = 0.01;

    public static final double BULLET_TIME_MULTIPLIER = 0.2;
    public static final int BULLET_TIME_DURATION = 3000;
    public static final int BULLET_TIME_COOLDOWN = 5000;
    public static final int BULLET_TIME_HEIGHT = 10;

    public static final int SCORE_AUTO_INCREMENT_TIME = 2000;
    public static final int SCORE_AUTO_INCREMENT_BASE_VALUE = 1;

    public static final int LEVEL_AUTO_INCREMENT_TIME = 20000;

    public static final double PARTICLE_DECELERATION = 0.00007;

    public static final int GAME_OVER_DELAY = 4000;

    public static final int BACKGROUND_Z_INDEX = 0;
    public static final int BONUS_PICKUP_Z_INDEX = 1;
    public static final int PARTICLE_Z_INDEX = 2;
    public static final int BONUS_Z_INDEX = 10;
    public static final int ENEMY_Z_INDEX = 20;
    public static final int PLAYER_Z_INDEX = 100;
    public static final int HEADER_Z_INDEX = 150;
    public static final int SCORE_Z_INDEX = 151;
    public static final int LEVEL_Z_INDEX = 152;
    public static final int BULLET_TIME_Z_INDEX = 160;

    public static final float CLICK_SOUND_VOLUME = 0.9f;
    public static final float START_SOUND_VOLUME = 0.9f;
    public static final float BONUS_PICKUP_SOUND_VOLUME = 0.6f;
    public static final float DEATH_SOUND_VOLUME = 0.9f;
    public static final float MENU_MUSIC_VOLUME = 0.9f;
    public static final float GAME_MUSIC_VOLUME = 0.9f;
    public static final float GAME_OVER_MUSIC_VOLUME = 0.9f;
}
