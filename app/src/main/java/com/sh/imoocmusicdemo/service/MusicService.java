package com.sh.imoocmusicdemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.WelcomeActivity;
import com.sh.imoocmusicdemo.helps.MediaPlayerHelp;
import com.sh.imoocmusicdemo.models.MusicModel;

/**
 * 1.通过service连接playmusic和mediahelper
 * 2.playmusicview--service
 *      1.播放音乐、暂停音乐
 *      2.启动service、绑定service，解除绑定
 * 3.MediaPlayHelper--service
 *      1.播放音乐、暂停音乐
 *      2.监听音乐播放完成，停止service
 */
public class MusicService extends Service {

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;
    //不可为0
    public static final int NOTIFICATION_ID=1;

    public MusicService() {
    }

    public class MusicBind extends Binder{
        /**
         * 设置音乐(musicmodel)
         */
        public void setMusic(MusicModel musicModel){
            mMusicModel=musicModel;
            startForeground();
        }

        /**
         * 播放音乐
         */
        public void playMusic(){
            /**
             * 1.当前的音乐是否已经在播放的音乐
             * 2.如果当前的音乐是已经在播放的音乐的话，那么就直接执行start方法
             * 3.如果当前播放的音乐不是当前需要的音乐，那么就调用setPath方法
             */
            if(mMediaPlayerHelp.getPath()!=null
                    &&mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())){
//                Log.d("MusicService","222"+mMusicModel.toString());
                mMediaPlayerHelp.start();
            }else {
//                Log.d("MusicService",mMusicModel.toString());
                mMediaPlayerHelp.setpath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * 暂停播放
         */
        public void stopMusic(){
            mMediaPlayerHelp.pause();
        }


        /**
         * 获取歌曲长度
         */
        public int getMusicDuration(){
            return mMediaPlayerHelp.getMusicDuration();
        }

        /**
         * 获取当前歌曲长度
         */
        public  int getMusicCurrentPosition(){
            return mMediaPlayerHelp.getMusicCurrentPosition();
        }


        /**
         * 追到当前进度
         * @param position
         */
        public void seektoCurrent(int position){
            mMediaPlayerHelp.seekTo(position);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerHelp=MediaPlayerHelp.getInstance(this);
    }

    /**
     * 系统默认不允许不可见的后台服务播放音乐，
     * Notification，
     */
    /**
     * 设置服务在前台可见
     */
    private void startForeground(){

        /**
         * 通知栏点击跳转的intent
         */
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0
                ,new Intent(this, WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);

        /**
         * 创建notification
         */
        Notification notification=new Notification.Builder(this)
                .setContentTitle(mMusicModel.getName())
                .setContentText(mMusicModel.getAuthor())
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent)
                .build();

        /**
         * 设置notification在前台展示
         */
        startForeground(NOTIFICATION_ID,notification);
    }




}
