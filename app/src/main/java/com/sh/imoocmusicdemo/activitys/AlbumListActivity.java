package com.sh.imoocmusicdemo.activitys;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.adapts.MusicListAdapter;
import com.sh.imoocmusicdemo.helps.RealmHelp;
import com.sh.imoocmusicdemo.models.AlbumModel;

public class AlbumListActivity extends BaseActivity {

    private RecyclerView mRvList;
    private MusicListAdapter mAdapter;
    public static final String AlbumId="";
    private String mAlbumId;
    private RealmHelp mRealmHelp;
    private AlbumModel mAlbumModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        initData();
        initView();
    }

    private void initView(){
        initNavBar(true,"专辑列表",false);
        mRvList=findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        mAdapter=new MusicListAdapter(this,null,mAlbumModel.getList());
        mRvList.setAdapter(mAdapter);
    }

    private void initData(){
        mAlbumId=getIntent().getStringExtra(AlbumId);
        mRealmHelp=new RealmHelp();
        mAlbumModel=mRealmHelp.getAlbum(mAlbumId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelp.close();
    }
}
