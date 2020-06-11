package com.sh.imoocmusicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.dialog.CustomDialog;
import com.sh.imoocmusicdemo.helps.UserHelper;
import com.sh.imoocmusicdemo.utils.ToastUtil;
import com.sh.imoocmusicdemo.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }


    private void initView(){
        initNavBar(true,"个人中心",false);

        mTvUser=findViewById(R.id.tv_user);
        mTvUser.setText("用户名"+ UserHelper.getInstance().getPhone());
    }

    /**
     * 修改密码
     */
    public void onChangeClick(View view){
        startActivity(new Intent(this,ChangePasswordActivity.class));
    }

    /**
     * 退出登录
     */
    public void onLogoutClick(View view){
        CustomDialog customDialog=new CustomDialog(MeActivity.this);
        customDialog.setCancelable(false);
        customDialog.setTitle("提示").setMessage("确认退出")
                .setCancel("取消", new CustomDialog.IonCancelListener() {
                    @Override
                    public void onCancel(CustomDialog customDialog) {
                      customDialog.dismiss();
                    }
                }).setConfirm("确认", new CustomDialog.IonConfirmListener() {
            @Override
            public void onConfrim(CustomDialog customDialog) {
                UserUtils.logout(MeActivity.this);
            }
        }).show();

    }

}
