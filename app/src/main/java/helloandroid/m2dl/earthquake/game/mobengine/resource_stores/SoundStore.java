package helloandroid.m2dl.earthquake.game.mobengine.resource_stores;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game_menu.BackgroundSoundService;

public class SoundStore {

    private static final Map<Integer, MediaPlayer> mediaPlayerMap = new HashMap<>();

    private static Map<MediaPlayer, Integer> pausedMediaPlayers;

    private static boolean mute = false;


    private static Intent mainMenuBackgroundSoundIntent;

    public static void createMainMenuBackgroundSoundIntent(Context context){
        mainMenuBackgroundSoundIntent = new Intent(context, BackgroundSoundService.class);
    }

    public static Intent getMainMenuBackgroundSoundIntent() {
        return mainMenuBackgroundSoundIntent;
    }

    private static MediaPlayer click ;

    public static void createClickMediaPlayer(Context context){
        click = MediaPlayer.create(context, R.raw.click);
    }

    public static void playClickMediaPlayer(){
        if(!mute) {
            click.start();
        }
    }

    private static MediaPlayer startSound ;

    public static void createStartSoundMediaPlayer(Context context){
        startSound = MediaPlayer.create(context, R.raw.start);
    }

    public static void playStartSoundMediaPlayer(){
        if(!mute) {
            startSound.start();
        }
    }

    private static MediaPlayer gameOverSound ;

    public static void createGameOverSoundMediaPlayer(Context context){
        gameOverSound = MediaPlayer.create(context, R.raw.libre_de_droit);
    }

    public static void playGameOverSoundMediaPlayer(){
        if(!mute) {
            gameOverSound.start();
        }
    }

    public static void stopGameOverSoundMediaPlayer(){
        if(!mute) {
            gameOverSound.stop();
        }
    }

    public static void createMediaPlayers(int[] soundIds, Context context) {
        pausedMediaPlayers = new HashMap<>();
        for(Integer soundId : soundIds) {
            mediaPlayerMap.put(soundId, MediaPlayer.create(context, soundId));
        }
    }

    public static void releaseMediaPlayers() {
        for(MediaPlayer mp : mediaPlayerMap.values()) {
            mp.release();
        }
        mediaPlayerMap.clear();
    }

    public static void playSound(int soundId, float volume) {
        if(!mute) {
            mediaPlayerMap.get(soundId).setVolume(volume, volume);
            mediaPlayerMap.get(soundId).seekTo(0);
            mediaPlayerMap.get(soundId).start();
        }
    }

    public static void loopSound(int soundId, float volume) {
        if(!mute){
            mediaPlayerMap.get(soundId).setLooping(true);
            mediaPlayerMap.get(soundId).setVolume(volume, volume);
            mediaPlayerMap.get(soundId).seekTo(0);
            mediaPlayerMap.get(soundId).start();
            }
    }

    public static void stopLoopedSound(int soundId) {
        mediaPlayerMap.get(soundId).setLooping(false);
        mediaPlayerMap.get(soundId).stop();
    }

    public static void pauseAll() {
        for(MediaPlayer mp : mediaPlayerMap.values()) {
            if(mp.isPlaying()) {
                mp.pause();
                pausedMediaPlayers.put(mp,mp.getCurrentPosition());
            }
        }
    }

    public static void startAllPaused() {
        Set setOfKey = pausedMediaPlayers.keySet();
        Iterator i = setOfKey.iterator();
        while (i.hasNext()){
            MediaPlayer mp = (MediaPlayer) i.next();
            Integer truc = pausedMediaPlayers.get(mp);
            mp.seekTo(truc);
            mp.start();
        }

        /*
        for(MediaPlayer mp : pausedMediaPlayers) {
            mp.
            mp.start();
        }
        */

        pausedMediaPlayers.clear();
    }

    public static boolean isMute() {
        return mute;
    }

    public static void setMute(boolean mute) {
        SoundStore.mute = mute;
    }
}
