package com.sh.imoocmusicdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.sh.imoocmusicdemo.R;

/**
 * @Author: songhui
 * @Description:
 * 1.input_icon:输入框前面的图标
 * 2.input_hint： 输入的提示内容
 * 3.is——password：输入的内容是否需要以秘文的形式展示
 * @Date: 2020/2/5 15:41
 */
public class InputView  extends FrameLayout {

    private int inputIcon;
    private String inputHint;
    private boolean ispassword;

    private View mView;

    private ImageView mTvIcon;
    private EditText mEtInput;

    public InputView(Context context) {
        super(context);
        init(context,null);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    protected void init(Context context,AttributeSet attrs){
        if(attrs==null) return;

        //自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.inputView);
        inputIcon = typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.logo);
        inputHint=typedArray.getString(R.styleable.inputView_input_hint);
        ispassword=typedArray.getBoolean(R.styleable.inputView_is_password,false);
        typedArray.recycle(); //释放

        //绑定layout布局
        mView=LayoutInflater.from(context).inflate(R.layout.input_view,this,false);
        mTvIcon=mView.findViewById(R.id.iv_icon);
        mEtInput=mView.findViewById(R.id.ed_input);

        //布局关联属性
        mTvIcon.setImageResource(inputIcon);
        mEtInput.setHint(inputHint);
        mEtInput.setInputType(ispassword? InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD:InputType.TYPE_CLASS_PHONE);

        addView(mView);
    }

    /**
     * 获取输入内容
     * @return
     */
    public String getInputStr(){
        return mEtInput.getText().toString().trim();
    }

    /**
     * 注册返回給phone的edittext赋值
     */
    public void setphoneedittext(String phone){
        mEtInput.setText(phone);
    }
}
