package com.yangkai.androiduikj.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangkai.androiduikj.R;
/**
 * Created by yangkai on 2017/2/22.
 */

public class ExpandTextView extends LinearLayout {

    private float density;
    private TextView mLeftTextView;
    private TextView mDivideView;
    private TextView mRightTextView;

    //设置默认值
    private static final float DefaultTextSize = 16;
    private static final int DefaultTextColor = Color.BLACK;
    private static final float DefaultLeftTextSize = 16;
    private static final int DefaultLeftTextColor = Color.BLACK;
    private static final float DefaultRightTextSize = 16;
    private static final int DefaultRightTextColor = Color.BLACK;

    private static final float mDividePadding = 0;//控件间距
    private static final float mDivideLeftPadding = 0;//控件间距
    private static final float mDivideRightPadding = 0;//控件间距

    public ExpandTextView(Context context) {
        super(context);
        initView(context,null,0);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs,int defStyleAttr){
        setOrientation(HORIZONTAL);
        density = context.getResources().getDisplayMetrics().density;
        mLeftTextView = new TextView(context);
        mDivideView = new TextView(context);
        mRightTextView = new TextView(context);
        //获取自定义属性的值
        if (attrs != null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandTextView,defStyleAttr,0);
            String leftString = a.getString(R.styleable.ExpandTextView_leftText);
            mLeftTextView.setText(leftString);
            String rightString = a.getString(R.styleable.ExpandTextView_rightText);
            mRightTextView.setText(rightString);
            if(a.hasValue(R.styleable.ExpandTextView_textColor)){
                int textColor = a.getColor(R.styleable.ExpandTextView_textColor,DefaultTextColor);
                mLeftTextView.setTextColor(textColor);
                mRightTextView.setTextColor(textColor);
            }else{
                int mLeftTextColor = a.getColor(R.styleable.ExpandTextView_leftTextColor, DefaultLeftTextColor);
                int mRightTextColor = a.getColor(R.styleable.ExpandTextView_rightTextColor, DefaultRightTextColor);
                mLeftTextView.setTextColor(mLeftTextColor);
                mRightTextView.setTextColor(mRightTextColor);
            }

            if(a.hasValue(R.styleable.ExpandTextView_textSize)){
                float textSize = a.getDimension(R.styleable.ExpandTextView_textSize, DefaultTextSize) / density;
                mLeftTextView.setTextSize(textSize);
                mRightTextView.setTextSize(textSize);
            }else{
                float mLeftTextSize = a.getDimension(R.styleable.ExpandTextView_leftTextSize, DefaultLeftTextSize) / density;
                mLeftTextView.setTextSize(mLeftTextSize);
                float mRightTextSize = a.getDimension(R.styleable.ExpandTextView_rightTextSize, DefaultRightTextSize) / density;
                mRightTextView.setTextSize(mRightTextSize);
            }

            if(a.hasValue(R.styleable.ExpandTextView_dividePadding)){
                float dividePadding = a.getDimension(R.styleable.ExpandTextView_dividePadding,mDividePadding);
                mDivideView.setPadding((int)dividePadding,0,(int)dividePadding,0);
            }else{
                float mDivideLeft = a.getDimension(R.styleable.ExpandTextView_divideLeftPadding,mDivideLeftPadding);
                float mDivideRight = a.getDimension(R.styleable.ExpandTextView_divideRightPadding,mDivideRightPadding);
                mDivideView.setPadding((int)mDivideLeft,0,(int)mDivideRight,0);
            }
            a.recycle();
        }
        addView(mLeftTextView, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(mDivideView, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(mRightTextView, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    }

    /**
     *  设置左端字体颜色
     * @param leftColor
     */
    public void setLeftTextColor(int leftColor){
            mLeftTextView.setTextColor(leftColor);
    }

    /**
     *  设置右端字体颜色
     * @param rightColor
     */
    public void setRightTextColor(int rightColor){
            mRightTextView.setTextColor(rightColor);
    }

    public void setLeftText(int leftTextRes){
        if(leftTextRes > 0){
            mLeftTextView.setText(leftTextRes);
        }
    }

    public void setRightText(CharSequence rightTextRes){
        mRightTextView.setText(rightTextRes);
    }

    public void setLeftText(String leftTextRes){
        mLeftTextView.setText(leftTextRes);
    }

    public void setRightText(int rightTextRes){
        if(rightTextRes > 0){
            mRightTextView.setText(rightTextRes);
        }
    }

    public CharSequence getRightText(){
        return mRightTextView.getText();
    }
}
