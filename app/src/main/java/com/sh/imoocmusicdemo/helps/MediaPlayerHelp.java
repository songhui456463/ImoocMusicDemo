package com.sh.imoocmusicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;

/**
 * 1.直接在activity中创建播放音乐，音乐与activity绑定，activity运行时播放音乐，activity退出时音乐就会停止
 * 2.通过全局单例类与application绑定，application运行时播放音乐，application退出时音乐就会停止
 * 3.通过service进行音乐播放，service运行时播放音乐，service退出时音乐就会停止
 */

public class MediaPlayerHelp  {


    private static MediaPlayerHelp instance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;
    private String mPath;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public static MediaPlayerHelp getInstance(Context context){

        if(instance==null){
            synchronized (MediaPlayerHelp.class){
                if(instance==null){
                    instance=new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }


    private MediaPlayerHelp(Context context){
        this.mContext=context;
        mMediaPlayer=new MediaPlayer();
    }

    /**
     * 1.setPath:当前需要播放的音乐
     * 2.start：播放音乐
     * 3.pause：暂停播放
     */

    /**
     * 当前需要播放的音乐
     * @param path
     */
    public void setpath(String path){
        /**
         * 1.音乐正在播放，或者切换了音乐。重置音乐播放状态
         * 2.设置播放音乐路径
         * 3.准备播放
         */
        //当音乐正在切换的时候，音乐处于播放状态，那我们重置音乐播放状态
        //如果音乐没有处于播放状态，那我们不重置音乐播放状态

        if(mMediaPlayer.isPlaying()||!path.equals(mPath)){
            mMediaPlayer.reset();
        }

        mPath=path;
        //2.
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.异步加载
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });
        //监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });
    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath(){
        return mPath;
    }

    //播放音乐
    public void start(){
        if(mMediaPlayer.isPlaying())return;
        mMediaPlayer.start();
    }

    //暂停播放
    public void pause(){
        mMediaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mediaPlayer);
        void onCompletion(MediaPlayer mp);
    }

    //获取歌曲长度
    public int getMusicDuration()
    {
        int rtn = 0;
        if (mMediaPlayer != null)
        {
            rtn = mMediaPlayer.getDuration();
        }

        return rtn;
    }

    //获取当前播放进度
    public int getMusicCurrentPosition()
    {
        int rtn = 0;
        if (mMediaPlayer != null)
        {
            rtn = mMediaPlayer.getCurrentPosition();

        }

        return rtn;
    }

    /**
     * 更新进度
     * @param position
     */
    public void seekTo(int position)
    {
        if (mMediaPlayer != null)
        {
            mMediaPlayer.seekTo(position);
        }
    }

    /**
     * 音乐停止播放
     */
    public void stopPlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

}
