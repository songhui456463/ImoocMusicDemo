package com.sh.imoocmusicdemo.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

//AppCompatImageView
public class WEqualsImageView extends AppCompatImageView {

    public WEqualsImageView(Context context) {
        super(context);
    }

    public WEqualsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        //获取view宽度
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        //获取view模式
//        //match_parent.warp_content,具体dp
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }

}
