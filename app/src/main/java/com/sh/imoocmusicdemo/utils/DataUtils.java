package com.sh.imoocmusicdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtils {

    /**
     * 读取资源文件的数据
     */
    public static String getJsonFromAssets(Context context,String fileName){
        /**
         * 1.StringBuilder存放读取出的数据
         * 2.AssetManager资源管理器，open方法打卡指定的资源文件夹，返回InputStream输入流
         * 3.InputStreamReader（字节到字符的桥接器）,BufferedReader(存放读取字节缓冲区)
         * 4.循环利用BufferedReader的Readline方法读取每一行数据，并且把读取数据放入StringBuilder里面
         * 5.返回取出来的所有数据
         */

        //StringBuilder存放读取出的数据
        StringBuilder s=new StringBuilder();
        //AssetManager资源管理器
        AssetManager assetManager=context.getAssets();
        //open方法打开指定的资源文件，返回输入流InputStream输入流
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            while ((line=bufferedReader.readLine())!=null){
                s.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
