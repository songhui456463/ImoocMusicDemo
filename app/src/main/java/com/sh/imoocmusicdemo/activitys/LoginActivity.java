package com.sh.imoocmusicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.views.InputView;

//navigationbar

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassowrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }


    /**
     * 初始化view
     */
    protected  void initView(){
        initNavBar(false,"登录",false);

        mInputPhone=findViewById(R.id.input_phone);
        mInputPassowrd=findViewById(R.id.input_password);
    }

    /**
     * 跳转注册页面点击事件
     */
    public void onRegisterClick(View view){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录
     */
    public void onCommitClick(View view){
        String phone=mInputPhone.getInputStr();
        String password=mInputPassowrd.getInputStr();

//        Log.d("LoginActivity",phone);
//        Log.d("LoginActivity",password);

        //验证用户输入是否合法
//        if(!UserUtils.validateLogin(this,phone,password)){
//            return;
//        }

        //跳转到应用主页
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
