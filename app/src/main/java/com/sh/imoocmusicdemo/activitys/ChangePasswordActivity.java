package com.sh.imoocmusicdemo.activitys;

import android.os.Bundle;
import android.view.View;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.utils.UserUtils;
import com.sh.imoocmusicdemo.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mEtOldPassword,mEtPassword,mEtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }


    private void initView(){
        initNavBar(true,"修改密码",false);
        mEtOldPassword=findViewById(R.id.input_old_password);
        mEtPassword=findViewById(R.id.input_password);
        mEtPasswordConfirm=findViewById(R.id.input_password_confirm);
    }

    public void onChangePasswordClick(View view){
        String oldPassword=mEtOldPassword.getInputStr();
        String password=mEtPassword.getInputStr();
        String passwordConfirm=mEtPasswordConfirm.getInputStr();

        boolean result = UserUtils.changePassword(this, oldPassword, password, passwordConfirm);
        if(!result){
            return;
        }
        UserUtils.logout(this);
    }

}
