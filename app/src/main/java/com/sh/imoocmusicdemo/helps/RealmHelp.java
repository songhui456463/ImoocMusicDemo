package com.sh.imoocmusicdemo.helps;

import android.content.Context;

import com.sh.imoocmusicdemo.migration.Migration;
import com.sh.imoocmusicdemo.models.AlbumModel;
import com.sh.imoocmusicdemo.models.MusicModel;
import com.sh.imoocmusicdemo.models.MusicSourceModel;
import com.sh.imoocmusicdemo.models.UserModel;
import com.sh.imoocmusicdemo.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelp {

    private Realm mRealm;

    public RealmHelp() {
        mRealm = Realm.getDefaultInstance();//内存计数加一
    }


    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
//        mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();
    }


    /**
     * 返回所有用户
     */
    public List<UserModel> getAllUser(){
        RealmQuery<UserModel> query=mRealm.where(UserModel.class);
        RealmResults<UserModel> all = query.findAll();
        return all;
    }


    /**
     * 验证用户信息
     */
    public boolean validateUser(String phone,String password){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone", phone).equalTo("password", password);

        UserModel userModel = query.findFirst();

        if(userModel!=null){
            return true;
        }
            return false;

    }

    /**
     * 获取当前用户
     */
    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }


    /**
     * 获取登录用户密码
     * @return
     */
    public UserModel getloginUserPwd(String phone){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", phone).findFirst();
        return userModel;
    }

    /**
     * 修改密码
     */
    public void changePassword(String password){
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 登录修改密码
     */
    public void loginChangePassword(String phone,String password){
        UserModel userModel = getloginUserPwd(phone);
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }


    /**
     * 1.用户登录，存放数据
     * 2.用户退出，删除数据
     */

    /**
     * 保存音乐原数据
     */
    public void setMusicSource(Context context){
        //拿到资源文件的数据
        String musicSourceJosn = DataUtils.getJsonFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class,musicSourceJosn);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐数据
     * 1.RealmResult.delete
     * 2.Realm delete删除这个模型下的所有的数据
     */
    public void removeMusicSource(){
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }

    /**
     * Realm数据库发生结构性变化(模型或者模型中的字段发生了新增、修改、删除
     * )的时候，我们需要对数据库进行迁移。
     */

    /**
     * 告诉Realm数据需要迁移，并且为Realm设置最新的配置
     */
    public static void migration(){
        RealmConfiguration conf=getRealmConf();

        //Realm设置最新的配置
        Realm.setDefaultConfiguration(conf);
        //告诉Realm数据需要迁移
        try {
            Realm.migrateRealm(conf);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    /**
     * 返回 RealmConfiguration
     * @return
     */
    private static RealmConfiguration getRealmConf(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 返回音乐原数据
     */
    public MusicSourceModel getMusicSource(){
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumId){
       return mRealm.where(AlbumModel.class).equalTo("albumId",albumId).findFirst();
    }

    /**
     * 返回音乐
     */
    public MusicModel getMusic(String musicId){
        return mRealm.where(MusicModel.class).equalTo("musicId",musicId).findFirst();
    }

    /**
     * 关闭数据库
     */
    public void close(){
        if(mRealm!=null&&mRealm.isClosed()){
            mRealm.close();
        }
    }

}