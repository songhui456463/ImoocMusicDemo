package com.sh.imoocmusicdemo.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.helps.MediaPlayerHelp;
import com.sh.imoocmusicdemo.models.MusicModel;
import com.sh.imoocmusicdemo.service.MusicService;

public class PlayMusicView extends FrameLayout {


    private Context mContext;
    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private MusicModel mMusicModel;

    private View mView;
    private ImageView mIvIcon;

    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;

    private FrameLayout mFlPlayMusic;
    private ImageView mIvNeedle;
    private ImageView mIvPlay;
    private boolean isPlaying,isBindService;


    public PlayMusicView(Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        //MediaPlayer
        mContext=context;

        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);

        mIvIcon=mView.findViewById(R.id.iv_icon);
        mFlPlayMusic=mView.findViewById(R.id.f1_play_music);
        mFlPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        mIvNeedle=mView.findViewById(R.id.iv_needle);
        mIvPlay=mView.findViewById(R.id.iv_play);


        /**
         * 1.定义光盘执行的动画
         *      1.光盘的转到动画
         *      2.指针指向光盘的动画
         *      3 .指针离开光盘的动画
         *  2.startAnimation
         */
        mPlayMusicAnim=AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);
        mPlayNeedleAnim=AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);
        mStopNeedleAnim=AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);
        addView(mView);

    }


    /**
     * 设置光盘中显示的音乐封面图片
     */
    public void setMusicIcon(){
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }

    public void setMusic(MusicModel music){
        mMusicModel=music;
        setMusicIcon();
    }

    /**
     * 播放音乐
     */
    public void playMusic(){

        this.isPlaying=true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        startMusicService();
    }

    /**
     * 停止播放
     */
    public void stopMusic(){
        this.isPlaying=false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        if(mMusicBind!=null)
        mMusicBind.stopMusic();
    }

    /**
     * 切换播放状态
     */
    public void trigger(){
        if(isPlaying){
            stopMusic();
        }else {
            playMusic();
        }
    }

    /**
     * 启动音乐服务
     */
    private void startMusicService(){
        //启动service
        if(mServiceIntent==null){
            mServiceIntent=new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        }else {
            mMusicBind.playMusic();
        }

        //绑定 service，当前service未绑定，绑定服务
        if(!isBindService){
            isBindService=true;
            mContext.bindService(mServiceIntent,connection,Context.BIND_AUTO_CREATE);
        }

    }

    /**
     * 如果已经绑定了服务,解除绑定
     */
    public void destory(){
        if(isBindService){
            isBindService=false;
            mContext.unbindService(connection);
        }
    }

    ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBind= (MusicService.MusicBind) service;
            mMusicBind.setMusic(mMusicModel);
            Log.d("play",mMusicModel.toString());
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 获得当前进度
     */
    public int getCurrent(){
        return mMusicBind.getMusicCurrentPosition();
    }


    /**
     * 获得总进度
     */
    public int getToatl(){
        return mMusicBind.getMusicDuration();
    }


    /**
     * 切换歌曲
     */
    public void setMusicModel(MusicModel musicModel){
        mMusicBind.setMusic(musicModel);
    }

    /**
     * 追到当前进度
     */
    public void seek(int position){
        mMusicBind.seektoCurrent(position);
    }
}
