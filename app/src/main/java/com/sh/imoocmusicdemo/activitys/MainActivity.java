package com.sh.imoocmusicdemo.activitys;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.adapts.MusicGridAdapter;
import com.sh.imoocmusicdemo.adapts.MusicListAdapter;
import com.sh.imoocmusicdemo.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {

    //stausbar


    private RecyclerView mRvGrid,mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        initNavBar(false,"辉哥音乐",true);

        //网格布局
        mRvGrid=findViewById(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize),mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter=new MusicGridAdapter(this);
        mRvGrid.setAdapter(mGridAdapter);

        /**
         * 1.假如已知列表高度的情况下，可以直接在布局中把recyclerview的高度定义上
         * 2.不知道列表高度的情况下，需要手动计算recyclerview的高度
         */
        mRvList=findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter=new MusicListAdapter(this,mRvList);
        mRvList.setAdapter(mListAdapter);
    }



}
