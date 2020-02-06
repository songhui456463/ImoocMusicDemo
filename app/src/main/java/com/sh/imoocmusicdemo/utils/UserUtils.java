package com.sh.imoocmusicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.blankj.utilcode.util.RegexUtils;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.LoginActivity;
import com.sh.imoocmusicdemo.utils.ToastUtil;

public class UserUtils {
    /**
     * 验证登录用户输入合法性
     */
    public  static boolean validateLogin(Context context,String phone,String password){
        //简单的
//        RegexUtils.isMobileSimple(phone);
        //精确的
        if(TextUtils.isEmpty(phone.trim())){
            ToastUtil.showMsg(context,"请输入手机号");
            return false;
        };
        if(!RegexUtils.isMobileExact(phone)){
            ToastUtil.showMsg(context,"无效手机号");
            return false;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtil.showMsg(context,"请输入密码");
            return false;
        };
        return true;
    }

    /**
     * 退出登录
     */
    public  static  void logout(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        //添加intent标志符，清理task栈，并且新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义Activity跳转动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

}
