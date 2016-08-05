package com.miniram.onechat.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.miniram.onechat.R;

/**
 * Created by Hongsec on 2016-08-04.
 */
public class ShakeSound {

    private   int shake_sound_male;
    private   Context mContext;
    private  int shake_match;
    private   int shake_nomatch;
    private   SoundPool mSoundPool;
    private AudioManager mAudioManager;
    private int streamVolume= 1;
    private MyVolumeReceiver mVolumeReceiver;

    public ShakeSound(Context context) {
        this.mContext = context;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mSoundPool == null) {
                mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            }
        }else{
            AudioAttributes build1 = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_MEDIA).build();
            if (mSoundPool == null) {
                mSoundPool = new SoundPool.Builder().setAudioAttributes(build1).setMaxStreams(1).build();
            }
        }

        shake_match = mSoundPool.load(context, R.raw.shake_match,1);
        shake_nomatch = mSoundPool.load(context, R.raw.shake_nomatch, 1);
        shake_sound_male = mSoundPool.load(context, R.raw.shake_sound_male, 1);
    }


    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                streamVolume = RefreshStreamValue();
            }
        }
    }
    private boolean check_Reload_Sound() {
        boolean result = false;
        result |= Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        // in xiaomi  should return true must
        result |= "xiaomi".equalsIgnoreCase(Build.MANUFACTURER.toLowerCase());
        return result;
    }

    public void playMatchShake(){

        mSoundPool.play(shake_match,streamVolume,streamVolume,0,0,1);
        if (check_Reload_Sound()) {
//                        mSoundPool.stop(soundId);
            mSoundPool.unload(shake_match);
            shake_match = mSoundPool.load(mContext, R.raw.shake_match,1);
        }
    }
    public void playNoMatchShake(){

        mSoundPool.play(shake_nomatch,streamVolume,streamVolume,0,0,1);
        if (check_Reload_Sound()) {
//                        mSoundPool.stop(soundId);
            mSoundPool.unload(shake_nomatch);
            shake_nomatch = mSoundPool.load(mContext, R.raw.shake_nomatch,1);
        }
    }
    public void playMatchingShake(){

        mSoundPool.play(shake_sound_male,streamVolume,streamVolume,0,0,1);
        if (check_Reload_Sound()) {
//                        mSoundPool.stop(soundId);
            mSoundPool.unload(shake_sound_male);
            shake_sound_male = mSoundPool.load(mContext, R.raw.shake_sound_male,1);
        }
    }


    private int RefreshStreamValue( ) {

        if (mAudioManager == null) {
            mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        }

        if (mAudioManager == null) {
            return 0;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        } else {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
    }

    public void registerSound() {
        try {
            mVolumeReceiver = new MyVolumeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.media.VOLUME_CHANGED_ACTION");
            mContext.registerReceiver(mVolumeReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void unregisterSound() {

        try {
            mContext.unregisterReceiver(mVolumeReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mSoundPool != null) {
            mSoundPool.stop(shake_match);
            mSoundPool.stop(shake_nomatch);
            mSoundPool.stop(shake_sound_male);
            mSoundPool.release();
            mSoundPool = null;
        }
    }

}
