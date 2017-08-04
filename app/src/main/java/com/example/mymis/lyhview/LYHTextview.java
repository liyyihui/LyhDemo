package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

/**
 * 作者：LYH   2017/7/31
 * <p>
 * 邮箱：945131558@qq.com
 */

public class LYHTextview extends android.support.v7.widget.AppCompatTextView {
    private Paint mTextPaint;
    private int mStart,mEnd,mChangecolor,baseLine,width,height;
    private Context mContext;
    public LYHTextview(Context context) {
        this(context,null);

    }

    public LYHTextview(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public LYHTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getdata(context,attrs);
       init();

    }

    private void getdata(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LYHTextview);
       mStart = array.getInt(R.styleable.LYHTextview_start,-1);
       mEnd = array.getInt(R.styleable.LYHTextview_end,-1);
        mChangecolor = array.getColor(R.styleable.LYHTextview_changecolor,this.getCurrentTextColor());

        array.recycle();

    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mChangecolor);
        mTextPaint.setDither(true);//防抖动
        mTextPaint.setTextSize(getTextSize());
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        baseLine = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom + getHeight()/2
                +getPaddingTop()/2-getPaddingBottom()/2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        width = getWidth();
        height = getHeight();


        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        switch (mode){
            case MeasureSpec.EXACTLY:

                width = MeasureSpec.getSize(widthMeasureSpec);
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:

                width = MeasureSpec.getSize(widthMeasureSpec);
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:

                width = MeasureSpec.getSize(widthMeasureSpec);
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
        }



        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(mStart==-1||mEnd==-1){//正常文本
            canvastext(canvas);
        }else {
            if(mEnd>getText().toString().length()){
                Toast.makeText(mContext,"结束位置不能超过文本长度",Toast.LENGTH_SHORT).show();
                return;
            }
            canvastext(canvas,mStart,mEnd);
        }

      // super.onDraw(canvas);
    }
  // 绘制指定了颜色变化
    private void canvastext(Canvas canvas, int mStart, int mEnd) {
        String mText = getText().toString();
        String s = mText.substring(0, mStart);
        String s1 = mText.substring(mStart, mEnd);
       mTextPaint.setColor(this.getCurrentTextColor());
        canvas.drawText(s,getPaddingLeft(), ((gettextheight())),mTextPaint);
        mTextPaint.setColor(mChangecolor);
        canvas.drawText(s1,getPaddingLeft()+gettextwidth(s),((gettextheight())),mTextPaint);
        if(mEnd<mText.length()){
            String s2 = mText.substring(mEnd , mText.length());
            mTextPaint.setColor(this.getCurrentTextColor());
            canvas.drawText(s2,getPaddingLeft()+gettextwidth(s+s1),  ((gettextheight())),mTextPaint);
        }

    }
    /**
     * 文字宽度
     * */
    private float gettextwidth(String arr){
        return mTextPaint.measureText(arr);
    }
    /**
     * 无颜色变化的绘制方式
     * */
    private void canvastext(Canvas canvas){
        String mText = getText().toString();
           canvas.drawText(mText,getPaddingLeft(), ((gettextheight())),mTextPaint);
    }

    private float gettextheight(){
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }
}
