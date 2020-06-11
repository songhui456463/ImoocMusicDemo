package com.sh.imoocmusicdemo.adapts;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class LooperPageAdapter extends PagerAdapter {

    private List<Integer> mPics=null;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition=position%mPics.size();
        ImageView imageView=new ImageView(container.getContext());
        imageView.setImageResource(mPics.get(realPosition));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if(mPics!=null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public void setData(List<Integer> sPics) {
        this.mPics=sPics;
    }


    public int getDataRealSize(){
        if(mPics!=null){
            return mPics.size();
        }
        return 0;
    }
}
