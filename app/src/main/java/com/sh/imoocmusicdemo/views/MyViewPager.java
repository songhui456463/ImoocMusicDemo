package com.sh.imoocmusicdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    private OnViewPagerTouchListener mTouchListen=null;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnViewPager(OnViewPagerTouchListener listener){
        this.mTouchListen=listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mTouchListen!=null){
                    mTouchListen.onPagerTouch(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if(mTouchListen!=null){
                    mTouchListen.onPagerTouch(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnViewPagerTouchListener{
        void onPagerTouch(boolean isTouch);
    }

}
