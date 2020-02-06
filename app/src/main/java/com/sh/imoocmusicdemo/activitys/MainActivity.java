package com.sh.imoocmusicdemo.activitys;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sh.imoocmusicdemo.R;

public class MainActivity extends BaseActivity {

    //stausbar


    private RecyclerView mRvGrid;

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

    }



}
