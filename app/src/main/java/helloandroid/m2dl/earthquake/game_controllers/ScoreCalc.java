package helloandroid.m2dl.earthquake.game_controllers;

import android.os.Handler;

import helloandroid.m2dl.earthquake.entity.player.Player;
import helloandroid.m2dl.earthquake.game.GameView;

public class ScoreCalc {

    private int score = 0;
    private Player player;
    private boolean gameRunning;
    private GameView game;
    private Handler clock;
    private int time=0;

    public ScoreCalc(Player player, GameView game){
        this.player = player;
        this.gameRunning = true;
        this.game = game;
        this.clock = new Handler();

        clock.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(gameRunning) {
                    time++;
                    score += 100 * game.getLevel();
                    clock.postDelayed(this, 1000);
                    if(time == 10){
                        game.setLevel(game.getLevel()+1);
                    }
                }
            }
        }, 1500);


    }

    public void add(int value){
        score += value;
    }
    public int getScore() {
        return this.score;
    }






}
