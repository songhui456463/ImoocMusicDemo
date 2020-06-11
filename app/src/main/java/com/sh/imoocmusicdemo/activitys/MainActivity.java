package com.sh.imoocmusicdemo.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.adapts.LooperPageAdapter;
import com.sh.imoocmusicdemo.adapts.MusicGridAdapter;
import com.sh.imoocmusicdemo.adapts.MusicListAdapter;
import com.sh.imoocmusicdemo.helps.RealmHelp;
import com.sh.imoocmusicdemo.models.MusicSourceModel;
import com.sh.imoocmusicdemo.views.GridSpaceItemDecoration;
import com.sh.imoocmusicdemo.views.MyViewPager;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements MyViewPager.OnViewPagerTouchListener, ViewPager.OnPageChangeListener {

    //stausbar
    private RecyclerView mRvGrid,mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;
    private RealmHelp mRealmHelp;
    private MusicSourceModel mMusicSourceModel;


    private MyViewPager mViewPager;
    private LooperPageAdapter mLooperPageAdapter;
    private static List<Integer> sPics=new ArrayList<>();
    private Handler mHandler;
    private boolean mIsTouch;        //判断是否触摸
    private LinearLayout mPointContainer;

    static {
        sPics.add(R.mipmap.pic1);
        sPics.add(R.mipmap.pic2);
        sPics.add(R.mipmap.pic3);
        sPics.add(R.mipmap.pic4);
        sPics.add(R.mipmap.pic5);
        sPics.add(R.mipmap.pic6);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView(){
        initNavBar(false,"音乐播放器",true);


        mViewPager=findViewById(R.id.looper_pager);
        mLooperPageAdapter=new LooperPageAdapter();
        mLooperPageAdapter.setData(sPics);
        mViewPager.setAdapter(mLooperPageAdapter);
        mViewPager.setOnViewPager(this);
        mViewPager.addOnPageChangeListener(this);

        mPointContainer=findViewById(R.id.points_container);
        initPoint();
       mViewPager.setCurrentItem(mLooperPageAdapter.getDataRealSize()*10,false);

        mHandler=new Handler();

        //网格布局
        mRvGrid=findViewById(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize),mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter=new MusicGridAdapter(this,mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);

        /**
         * 1.假如已知列表高度的情况下，可以直接在布局中把recyclerview的高度定义上
         * 2.不知道列表高度的情况下，需要手动计算recyclerview的高度
         */
        mRvList=findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter=new MusicListAdapter(this,mRvList,mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);
    }

    private void initData(){
        mRealmHelp=new RealmHelp();
        mMusicSourceModel=mRealmHelp.getMusicSource();
//        Log.d("MainActivity",mMusicSourceModel.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelp.close();
    }

    private Runnable mLooperTask=new Runnable() {
        @Override
        public void run() {
            if(!mIsTouch) {
                int currentPosition = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(++currentPosition, true);
            }
            mHandler.postDelayed(this, 3000);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.post(mLooperTask);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mLooperTask);
    }

    @Override
    public void onPagerTouch(boolean isTouch) {
        this.mIsTouch=isTouch;
    }


    /**
     * 初始化轮滑点
     */
    public void initPoint(){
        for(int i=0;i<sPics.size();i++){
            View point=new View(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(30,30);
            point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));
            layoutParams.leftMargin=20;
            layoutParams.bottomMargin=10;
            point.setLayoutParams(layoutParams);
            mPointContainer.addView(point);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 海报
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        int realPosition;
        if(mLooperPageAdapter.getDataRealSize()!=0){
            realPosition=position%mLooperPageAdapter.getDataRealSize();
//            System.out.println(position);
//            System.out.println(mLooperPageAdapter.getDataRealSize());
        }else{
            realPosition=0;
        }
        setSelectPoint(realPosition);
    }

    private void setSelectPoint(int realPosition) {
        for(int i=0;i<mPointContainer.getChildCount();i++){
            View point=mPointContainer.getChildAt(i);
            if(i!=realPosition){
                point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));
            }else {
                point.setBackground(getResources().getDrawable(R.drawable.shape_point_selected));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
