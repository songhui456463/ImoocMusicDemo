package com.sh.imoocmusicdemo.activitys;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.helps.MediaPlayerHelp;
import com.sh.imoocmusicdemo.helps.RealmHelp;
import com.sh.imoocmusicdemo.models.MusicModel;
import com.sh.imoocmusicdemo.utils.FontCache;
import com.sh.imoocmusicdemo.utils.ToastUtil;
import com.sh.imoocmusicdemo.views.PlayMusicView;

import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    public static final String MUSIC_ID="musicId";
    public static String isPlaying="isPlaying";


    private ImageView mIvBg;
    private PlayMusicView mPlayMusicView;
    private String mMusicId;
    private MusicModel mMusicModel;
    private RealmHelp mRealmHelp;
    private TextView mTvName,mTvAuthor;
    private SeekBar mSeekBar;

    private TextView mTvCurrent,mTvTotal;
    private Integer total;
    Timer timer;
    TimerTask task;
    private Handler mHandler;
    private ImageView mIvPrev,mIvPause,mIvNext;
    private boolean mIspause;
    private Runnable mRunnable;

    private MediaPlayerHelp mediaPlayerHelp;
    private boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        //隐藏statusBar，全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();
    }


    //后退按钮点击事件
    public void onBackClick(View view){
        onBackPressed();
    }

    private void initView(){
        mediaPlayerHelp= MediaPlayerHelp.getInstance(this);
        mIvBg=findViewById(R.id.iv_bg);
        mTvName=findViewById(R.id.tv_name);
        mTvAuthor=findViewById(R.id.tv_author);
        mSeekBar=findViewById(R.id.seekBar);
        mTvCurrent=findViewById(R.id.listen_current);
        mTvTotal=findViewById(R.id.listen_total);
        mIvPrev=findViewById(R.id.iv_prev);
        mIvPause=findViewById(R.id.iv_pasue);
        mIvNext=findViewById(R.id.iv_next);

        //设置字体
        Typeface customFont = FontCache.getTypeface("font/Mallorca.ttf", this);
        mTvAuthor.setTypeface(customFont);
        Typeface customFont1 = FontCache.getTypeface("font/SanstuyBrush.otf", this);
        mTvName.setTypeface(customFont1);

        initPlayView(mMusicModel);
        initTask();

    }

    /**
     * 定时任务
     */
    private void initTask(){
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mPlayMusicView.getCurrent()/1000==total) {
                            //播放完自动切换至下一首
                            changeToNext();
                        }
                        mTvCurrent.setText(calculateTime(mPlayMusicView.getCurrent()/1000));
                        mSeekBar.setProgress(mPlayMusicView.getCurrent()/1000);
                        if (mSeekBar.getSecondaryProgress()<total)
                            mSeekBar.setSecondaryProgress(mSeekBar.getSecondaryProgress()+10);
                    }
                });
            }
        };
       timer.schedule(task,1000,1000);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("PlayMusicActivity",String.valueOf(mSeekBar.getProgress()));
                mediaPlayerHelp.seekTo(mSeekBar.getProgress()*1000);
            }
        });

        mSeekBar.setSecondaryProgress(5);


        mHandler=new Handler();
        mHandler.postDelayed(mRunnable=new Runnable() {
            @Override
            public void run() {
                total=mPlayMusicView.getToatl()/1000;
//                Log.d("playview",total.toString());
//                Log.d("playview",calculateTime(total));
                mSeekBar.setMax(total);
                mTvTotal.setText(calculateTime(mPlayMusicView.getToatl()/1000));
            }
        },800);
    }


    private void initData(){
        mMusicId=getIntent().getStringExtra(MUSIC_ID);
        mRealmHelp=new RealmHelp();
        mMusicModel= mRealmHelp.getMusic(mMusicId);
    }


    private void initPlayView(MusicModel musicModel){
        //glide-transformations
        Glide.with(this)
                .load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5,10)))
                .into(mIvBg);

        mTvName.setText(musicModel.getName());
        mTvAuthor.setText(musicModel.getAuthor());
        mPlayMusicView=findViewById(R.id.play_view);
//        mPlayMusicView.setMusicIcon(mMusicModel.getPoster());
        mPlayMusicView.setMusic(musicModel);
        mPlayMusicView.playMusic();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelp.close();
        mPlayMusicView.destory();
        timer.cancel();
        task.cancel();
        mHandler.removeCallbacksAndMessages(mRunnable);
    }

    //计算播放时间
    public String calculateTime(int time){
        int minute;
        int second;
        if(time > 60){
            minute = time / 60;
            second = time % 60;
            //分钟再0~9
            if(minute >= 0 && minute < 10){
                //判断秒
                if(second >= 0 && second < 10){
                    return "0"+minute+":"+"0"+second;
                }else {
                    return "0"+minute+":"+second;
                }
            }else {
                //分钟大于10再判断秒
                if(second >= 0 && second < 10){
                    return minute+":"+"0"+second;
                }else {
                    return minute+":"+second;
                }
            }
        }else if(time < 60){
            second = time;
            if(second >= 0 && second < 10){
                return "00:"+"0"+second;
            }else {
                return "00:"+ second;
            }
        }
        return null;
    }



    /**
     * 切换到下一首
     */
    private void changeToNext(){
        mPlayMusicView.stopMusic();
        Integer musicId=Integer.parseInt(mMusicId);
        mMusicId = String.valueOf(musicId + 1);
        mMusicModel = mRealmHelp.getMusic(mMusicId);
        if (mMusicModel==null){
            ToastUtil.showMsg(this,"对不起，已经到底了");
            return;
        }
        Log.d("playactivity",mMusicModel.toString());
        mPlayMusicView.setMusicModel(mMusicModel);
        initPlayView(mMusicModel);
    }


    /**
     * 切换到上一首
     */
    private void changeToPrev(){
        mPlayMusicView.stopMusic();
        Integer musicId=Integer.parseInt(mMusicId);
        mMusicId = String.valueOf(musicId - 1);
        mMusicModel = mRealmHelp.getMusic(mMusicId);
        if (mMusicModel==null){
            ToastUtil.showMsg(this,"对不起，已经到顶了");
            return;
        }
        Log.d("playactivity",mMusicModel.toString());
        mPlayMusicView.setMusicModel(mMusicModel);
        initPlayView(mMusicModel);
    }


    /**
     * 上一首
     */
    protected void prev(View view){
        changeToPrev();
        timer.cancel();
        task.cancel();
        mHandler.removeCallbacksAndMessages(mRunnable);
        initTask();
    }

    /**
     * 下一首
     * @param view
     */
    protected void next(View view){
        changeToNext();
        timer.cancel();
        task.cancel();
        mHandler.removeCallbacksAndMessages(mRunnable);
        initTask();
    }

    /**
     * 暂停
     */
    protected void pause(View view){
        if(mIspause){
            mIvPause.setImageResource(R.mipmap.play1);
            mIspause=false;
        }else{
            mIvPause.setImageResource(R.mipmap.pause);
            mIspause=true;
        }
        mPlayMusicView.trigger();
    }


}
