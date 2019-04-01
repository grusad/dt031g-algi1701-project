package se.miun.algi1701.dt031g.dialer;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.DialogPreference;
import android.support.annotation.RawRes;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private static SoundPlayer instance;

    private SoundPool pool;
    private Map<String, Integer> ids = new HashMap<>();

    private SoundPlayer(Context context) {
        pool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        ids.put("0", pool.load(context, R.raw.zero, 1));
        ids.put("1", pool.load(context, R.raw.one, 1));
        ids.put("2", pool.load(context, R.raw.two, 1));
        ids.put("3", pool.load(context, R.raw.three, 1));
        ids.put("4", pool.load(context, R.raw.four, 1));
        ids.put("5", pool.load(context, R.raw.five, 1));
        ids.put("6", pool.load(context, R.raw.six, 1));
        ids.put("7", pool.load(context, R.raw.seven, 1));
        ids.put("8", pool.load(context, R.raw.eight, 1));
        ids.put("9", pool.load(context, R.raw.nine, 1));
        ids.put("*", pool.load(context, R.raw.star, 1));
        ids.put("#", pool.load(context, R.raw.pound, 1));


    }

    public static SoundPlayer getInstance(Context context){

        if(instance == null){
            instance = new SoundPlayer(context);
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

    public void destroy(){
        pool.release();
        instance = null;
    }
}
