package helloandroid.m2dl.earthquake.game.mobengine.statics;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundStore {

    private static Map<Integer, MediaPlayer> mediaPlayerMap;

    private static List<MediaPlayer> pausedMediaPlayers;

    private static boolean animationActive;

    public SoundStore(){
        animationActive = true;
    }

    public static void setAnimationActive(boolean act){
        animationActive = act;
    }

    public static boolean getAnimationActive(){
        return animationActive;
    }

    public static void createMediaPlayers(int[] soundIds, Context context) {
        pausedMediaPlayers = new ArrayList<>();
        mediaPlayerMap = new HashMap<>();
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
        if(!animationActive){
            return;
        }
        mediaPlayerMap.get(soundId).setVolume(volume, volume);
        mediaPlayerMap.get(soundId).seekTo(0);
        mediaPlayerMap.get(soundId).start();
    }

    public static void loopSound(int soundId, float volume) {
        if(!animationActive){
            return;
        }
        mediaPlayerMap.get(soundId).setLooping(true);
        mediaPlayerMap.get(soundId).setVolume(volume, volume);
        mediaPlayerMap.get(soundId).seekTo(0);
        mediaPlayerMap.get(soundId).start();
    }

    public static void stopLoopedSound(int soundId) {
        mediaPlayerMap.get(soundId).setLooping(false);
        mediaPlayerMap.get(soundId).stop();
    }

    public static void pauseAll() {
        for(MediaPlayer mp : mediaPlayerMap.values()) {
            if(mp.isPlaying()) {
                mp.pause();
                pausedMediaPlayers.add(mp);
            }
        }
    }

    public static void startAllPaused() {
        for(MediaPlayer mp : pausedMediaPlayers) {
            mp.start();
        }
        pausedMediaPlayers.clear();
    }
}
