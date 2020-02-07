package com.sh.imoocmusicdemo.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
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

public class PlayMusicView extends FrameLayout {


    private Context mContext;
    private View mView;
    private ImageView mIvIcon;

    private Animation mPlayMusicAnim,mPlayNeedleAnim,mStopNeedleAnim;

    private FrameLayout mFlPlayMusic;
    private ImageView mIvNeedle;
    private ImageView mIvPlay;
    private boolean isPlaying;

    private MediaPlayerHelp mMediaPlayerHelp;
    private String mPath;


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

        mMediaPlayerHelp=MediaPlayerHelp.getInstance(mContext);
    }


    /**
     * 设置光盘中显示的音乐封面图片
     */
    public void setMusicIcon(String icon){
        Glide.with(mContext)
                .load(icon)
                .into(mIvIcon);
    }

    /**
     * 播放音乐
     */
    public void playMusic(String path){
        mPath=path;
        this.isPlaying=true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        /**
         * 1.当前的音乐是否已经在播放的音乐
         * 2.如果当前的音乐是已经在播放的音乐的话，那么就直接执行start方法
         * 3.如果当前播放的音乐不是当前需要的音乐，那么就调用setPath方法
         */
        if(mMediaPlayerHelp.getPath()!=null
                &&mMediaPlayerHelp.getPath().equals(path)){
            mMediaPlayerHelp.start();
        }else {
            mMediaPlayerHelp.setpath(path);
            mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }

    /**
     * 停止播放
     */
    public void stopMusic(){
        this.isPlaying=false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        mMediaPlayerHelp.pause();
    }

    /**
     * 切换播放状态
     */
    private void trigger(){
        if(isPlaying){
            stopMusic();
        }else {
            playMusic(mPath);
        }
    }
}
