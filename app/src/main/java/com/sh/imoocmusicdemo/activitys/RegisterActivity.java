package com.sh.imoocmusicdemo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sh.imoocmusicdemo.R;


public class RegisterActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    /**
     * 初始化View
     */
    private void initView(){
        initNavBar(true,"注册页面",false);
    }

}
