package com.example.user.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


import com.example.user.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/4/24.
 */

public class VerticalTextView extends View {
    public static final String TAG = "VerticalTextView";
    private Paint mTextPaint;
    private int mTextColor;
    private int mTextSize;
    private int mColumeSpace;//列间距
    private int mTextSpace;//字间距
    private CharSequence mText;//字符
    private int width;
    private int height;
    private int mcharHeight;//字宽
    private List<String> mLines = new ArrayList<>();


    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView, defStyleAttr, 0);

        mTextColor = a.getColor(R.styleable.VerticalTextView_textColor, 0xff000000);
        mTextSize = a.getDimensionPixelSize(R.styleable.VerticalTextView_textSize, mTextSize);
        mColumeSpace = a.getDimensionPixelSize(R.styleable.VerticalTextView_columeSpacing, mColumeSpace);
        mTextSpace = a.getDimensionPixelSize(R.styleable.VerticalTextView_textSpace, mTextSpace);

        mText = a.getString(R.styleable.VerticalTextView_text);
        a.recycle();
        initPaint();
        mcharHeight = getmcharHeight();
    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),
                "font/song_bold_huawen.ttf");
        mTextPaint.setTypeface(typeface);
    }

    private void init() {
        mText = "";
        mTextColor = getResources().getColor(R.color.colorAccent);
        mTextSize = sp2px(getContext(), 18);
        mColumeSpace = dp2px(getContext(), 10);
        mTextSpace = dp2px(getContext(), 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getDesiredHeight(mText.toString()) + getPaddingTop() + getPaddingBottom();
        }
        mLines.clear();
        getFormatTexts(mText.toString());
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mcharHeight * mLines.size() + mColumeSpace * (mLines.size() - 1) + getPaddingLeft() + getPaddingRight();
        }
        Log.i(TAG, "width: " + width + "height: " + height + "mcharHeight: " + mcharHeight);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = width - getPaddingRight();
        float y;

        for (int i = 0; i < mLines.size(); i++) {
            if (i == 0) {
                x = x - mcharHeight;
            } else {
                x = x - mcharHeight - mColumeSpace;
            }
            y = getPaddingTop();
            for (int j = 0; j < mLines.get(i).length(); j++) {
                y = y + mcharHeight;
                canvas.drawText(mLines.get(i).substring(j, j + 1), x, y, mTextPaint);
                Log.i(TAG, "x: " + x + "y: " + y + mLines.get(i).substring(j, j + 1));
            }
        }
    }

    private void getFormatTexts(String s) {
        int contentHeight = height - getPaddingTop() - getPaddingBottom();
        if (getDesiredHeight(s) > contentHeight) {
            int count = contentHeight / mcharHeight;
            int i=0;
            for ( ; i < getDesiredHeight(s) / contentHeight; i++) {
                mLines.add(s.substring(i * count, (i + 1) * count));
            }
            if (getDesiredHeight(s) % contentHeight != 0) {
                mLines.add(s.substring(i * count, s.length()));
            }
        } else {
            mLines.add(s);
        }
    }

    private int getDesiredHeight(String s) {
        return getmcharHeight() * s.length();
    }

    private int getmcharHeight() {
        return mcharHeight = (int) (Math.abs(mTextPaint.ascent()) + mTextSpace);
    }


    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public CharSequence getmText() {
        return mText;
    }

    public void setmText(CharSequence mText) {
        this.mText = mText;
        postInvalidate();
    }
}
