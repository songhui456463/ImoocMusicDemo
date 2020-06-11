package com.sh.imoocmusicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.LoginActivity;
import com.sh.imoocmusicdemo.helps.MediaPlayerHelp;
import com.sh.imoocmusicdemo.helps.RealmHelp;
import com.sh.imoocmusicdemo.helps.UserHelper;
import com.sh.imoocmusicdemo.models.UserModel;

import java.util.List;

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
        /**
         * 1.用户当前的手机号是否已经被注册
         * 2.用户当前的手机号和密码是否匹配
         */
        if(!UserUtils.userExistFromPhone(phone)){
            ToastUtil.showMsg(context,"当前用户未注册");
            return false;
        }

        RealmHelp realmHelp=new RealmHelp();
        boolean result = realmHelp.validateUser(phone, EncryptUtils.encryptMD5ToString(password));

        if(!result){
            ToastUtil.showMsg(context,"密码错误");
            return false;
        }


        //保存用户登录标记
        boolean b = SpUtils.saveUser(context, phone);
        if(!b){
            ToastUtil.showMsg(context,"系统出错,请稍后重试");
        }

        //保存用户标标记，放在单例类中
        UserHelper.getInstance().setPhone(phone);

        //保存音乐数据
        realmHelp.setMusicSource(context);
        realmHelp.close();
        ToastUtil.showMsg(context,"登录成功");
        return true;
    }


    /**
     * 退出登录
     */
    public  static  void logout(Context context){
        //删除SP保存的用户标记
        boolean isRemove = SpUtils.remove(context);
        if(!isRemove){
            ToastUtil.showMsg(context,"系统出错,请稍后重试");
            return;
        }else {
            //删除数据源
            RealmHelp realmHelp=new RealmHelp();
            realmHelp.removeMusicSource();
            realmHelp.close();
            ToastUtil.showMsg(context,"退出成功");
        }
        MediaPlayerHelp instance = MediaPlayerHelp.getInstance(context);
        instance.stopPlaying();
        Intent intent=new Intent(context, LoginActivity.class);
        //添加intent标志符，清理task栈，并且新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义Activity跳转动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

    /**
     * 注册用户
     * @param context
     * @param phone
     * @param password
     * @param passwordConfirm
     */
    public static boolean registerUser(Context context,String phone,String password,String passwordConfirm){
        if(TextUtils.isEmpty(phone.trim())){
            ToastUtil.showMsg(context,"请输入手机号");
            return false;
        };
        if(!RegexUtils.isMobileExact(phone)){
            ToastUtil.showMsg(context,"无效手机号");
            return false;
        }
        if(TextUtils.isEmpty(password)||StringUtils.isEmpty(passwordConfirm)){
            ToastUtil.showMsg(context,"密码和确认密码不能为空");
            return false;
        }
        if(!password.equals(passwordConfirm)){
            ToastUtil.showMsg(context,"确认密码有误");
            return false;
        }
        //用户当前的手机号是否已经被注册
        /**
         * 1.通过realm获取到当前已经注册的所有用户
         * 2.根据用户输入的手机号匹配查询的用户，如果可以匹配则证明手机号已经被注册，否则手机号还未被注册
         */

        if(UserUtils.userExistFromPhone(phone)){
            ToastUtil.showMsg(context,"该手机号已存在");
            return false;
        }


        UserModel userModel=new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);
        return true;
    }

    /**
     * 保存用户到数据库
     * @param userModel
     */
    public static void saveUser(UserModel userModel){
        RealmHelp realmHelp=new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    /**
     * 根据手机号判断用户是否存在
     */
    public static boolean userExistFromPhone(String phone){
        boolean result=false;

        RealmHelp realmHelp=new RealmHelp();
        List<UserModel> allusers=realmHelp.getAllUser();

        for(UserModel userModel:allusers){
            if(userModel.getPhone().equals(phone)){
                //当前的手机号已存在手机中
                result=true;
                break;
            }
        }
        realmHelp.close();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     */
    public static boolean validateUserLogin(Context context){
        return SpUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1.数据验证
     *      1.原密码输入
     *      2.新密码是否输入并且新密码与确定密码是否相同
     *      3.原密码输入正确
     *          1.Realm获取当前登录的用户模型
     *          2.根据用户模型在的保存密码匹配用户原密码
     * 2.利用Realm模型自动更新的特性完成密码修改
     */
    public static boolean changePassword(Context context,String oldPssword
            ,String password,String passwordConfirm){
        if(TextUtils.isEmpty(oldPssword)){
            ToastUtil.showMsg(context,"请输入原密码");
            return false;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtil.showMsg(context,"请输入新密码");
            return false;
        }
        if(TextUtils.isEmpty(passwordConfirm)){
            ToastUtil.showMsg(context,"请输入确认密码");
            return false;
        }
        if(!password.equals(passwordConfirm)){
            ToastUtil.showMsg(context,"新密码和确认密码不一致");
            return false;
        }

        //验证原密码是否正确
        RealmHelp realmHelp=new RealmHelp();
        UserModel userModel = realmHelp.getUser();

        if(!EncryptUtils.encryptMD5ToString(oldPssword).equals(userModel.getPassword())){
            ToastUtil.showMsg(context,"原密码不正确");
            realmHelp.close();
            return false;
        }

        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();
        return true;
    }
}
