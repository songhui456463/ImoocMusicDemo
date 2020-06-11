package com.sh.imoocmusicdemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil  {

    private static Toast toast;

    private ToastUtil(){};

    public static void showMsg(Context context,String message){
        if(toast==null) {
            synchronized (TextUtils.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                }
            }
        }else {
            toast.setText(message);
        }
        toast.show();
    }


}
