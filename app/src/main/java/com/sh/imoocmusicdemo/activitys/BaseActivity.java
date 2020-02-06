package com.sh.imoocmusicdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.sh.imoocmusicdemo.R;

/**
 * @Author: songhui
 * @Description: 让所有activity继承BaseActivity，用与描述活动共性
 * @Date: 2020/2/5 14:05
 */
public class BaseActivity extends Activity {

    private ImageView mTvBack,mTvMe;
    private TextView mTvTvTitle;

    /**
     * findViewById
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id){
        return  findViewById(id);
    }


    /**
     * 初始化NavigationBar
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected  void initNavBar(boolean isShowBack,String title,boolean isShowMe){
        mTvBack=fd(R.id.iv_back);
        mTvMe=fd(R.id.iv_me);
        mTvTvTitle=fd(R.id.tv_title);

        mTvBack.setVisibility(isShowBack?View.VISIBLE:View.GONE);
        mTvMe.setVisibility(isShowMe?View.VISIBLE:View.GONE);
        mTvTvTitle.setText(title);

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    //返回
            }
        });

        mTvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this,MeActivity.class));
            }
        });
    }
}
