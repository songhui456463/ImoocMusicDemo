package com.sh.imoocmusicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.sh.imoocmusicdemo.R;

import java.util.Timer;
import java.util.TimerTask;

//1.延迟3秒
//2.跳转页面
public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init(){
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //主线程
//                Log.e("WelcomeActivity","当前线程为:" +Thread.currentThread());
//                toMain();
                toLogin();
            }
        },2000);     //延迟3秒执行run
    }


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

}
