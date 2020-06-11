package com.sh.imoocmusicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

//1.延迟3秒
//2.跳转页面
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private Timer mTimer;

    private TextView tv;
    private int reclen=5;       //跳过倒计时
    Timer timer=new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag,flag);

        setContentView(R.layout.activity_welcome);

        initView();
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒

        handler=new Handler();
        handler.postDelayed(runnable=new Runnable() {
            @Override
            public void run() {
                onCheckUser();
            }
        },5000);


//        new Thread();

//        init();
    }

//    private void init(){
//        mTimer=new Timer();
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //主线程
////                Log.e("WelcomeActivity","当前线程为:" +Thread.currentThread());
////                toMain();
//                toLogin();
//            }
//        },1000);     //延迟3秒执行run
//    }


    //  /**+enter
    /**
     *跳转到MainActivity
     */
    private void toMain(){
        Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *跳转到LoginActivity
     */
    private void toLogin(){
        Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void initView(){
        tv=findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }


    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reclen--;
                    tv.setText("跳过"+reclen);
                    if(reclen<0){
                        timer.cancel();
                        tv.setVisibility(View.GONE);
                    }
                }
            });
        }
    };


    /**
     * 点击跳过
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv:
                onCheckUser();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
        }
    }


    private void onCheckUser(){
        final boolean isLogin = UserUtils.validateUserLogin(this);
        if (isLogin){
            toMain();
        }else {
            toLogin();
        }
    }

}
