package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private static SoundPlayer instance;

    private SoundPool pool;
    private Map<String, Integer> ids = new HashMap<>();

    private SoundPlayer() {
        pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public static SoundPlayer getInstance(){

        if(instance == null){
            instance = new SoundPlayer();
        }

        return instance;
    }

    public void playSound(DialpadButton button){
        CharSequence title = button.getTitleText();

        try{
            int id = ids.get(title);
            pool.play(id, 1, 1, 0, 0, 1);
        }catch (NullPointerException e){

        }

    }

    public void loadSounds(String path){
        if(!ids.isEmpty()){
            pool.release();
        }

        ids.put("0", pool.load(path + "/zero.mp3", 1));
        ids.put("1", pool.load(path + "/one.mp3", 1));
        ids.put("2", pool.load(path + "/two.mp3", 1));
        ids.put("3", pool.load(path + "/three.mp3", 1));
        ids.put("4", pool.load(path + "/four.mp3", 1));
        ids.put("5", pool.load(path + "/five.mp3", 1));
        ids.put("6", pool.load(path + "/six.mp3", 1));
        ids.put("7", pool.load(path + "/seven.mp3", 1));
        ids.put("8", pool.load(path + "/eight.mp3", 1));
        ids.put("9", pool.load(path + "/nine.mp3", 1));
        ids.put("*", pool.load(path + "/star.mp3", 1));
        ids.put("#", pool.load(path + "/pound.mp3", 1));

    }

    public void destroy(){
        pool.release();
        instance = null;
    }
}
