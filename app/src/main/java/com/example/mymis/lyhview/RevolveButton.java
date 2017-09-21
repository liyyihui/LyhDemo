package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.Scroller;

/**
 * 作者：LYH   2017/9/15
 * <p>
 * 邮箱：945131558@qq.com
 */

public class RevolveButton extends Button {
     private Paint mButtonPaint,mLinepaint;
    private Path mPath;
     private int rectfangle,linelength;
     private int linecolor,buttoncolor;
     private RectF mRf;
    private boolean isone;
     private int mWith,mHeight;
    private int lastx,lasty,nextx,nexty;
    private  int position =0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();//刷新界面
        }
    };
    private Scroller mScroller;
    public RevolveButton(Context context) {
        this(context,null);
    }

    public RevolveButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RevolveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        mScroller = new Scroller(getContext());
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RevolveButton);
        rectfangle =  array.getInt(R.styleable.RevolveButton_rectfangle,40);
        linelength =  array.getInt(R.styleable.RevolveButton_linelength,100);

        linecolor =  array.getColor(R.styleable.RevolveButton_linecolor,Color.parseColor("#ff0202"));
        buttoncolor =  array.getColor(R.styleable.RevolveButton_buttoncolor,Color.parseColor("#FF0022FC"));
        array.recycle();
        mLinepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinepaint.setStrokeWidth(8);
        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
       mWith = getWidth();
        mHeight = getHeight();
       mRf = new RectF(0,0,mWith,mHeight);
        double v = (mWith - rectfangle * 2) * 2 + (mHeight - rectfangle * 2) * 2 + rectfangle * 2 * 3.14;
        int i = 2 * (mWith + mHeight);
        double v1 = 2 * (mWith + mHeight - 4 * rectfangle) + 2 * 3.14;
        int i1 = i / linelength;
        int i2 = i % linelength;
     //   Log.e("LYH","带圆角周长"+v);
      //  Log.e("LYH","带圆角周长v1   "+v1);
        Log.e("LYH","几段"+i1);
        Log.e("LYH","几段"+i2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLinepaint.setPathEffect(null);
        mLinepaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinepaint.setColor(buttoncolor);
      canvas.drawRoundRect(mRf,rectfangle,rectfangle,mLinepaint);
        mLinepaint.setStyle(Paint.Style.STROKE);
        mLinepaint.setColor(linecolor);
       mLinepaint.setPathEffect(new CornerPathEffect(rectfangle));
        //canvas.clipRect(mRf);

       /* mPath.moveTo(0,linelength/2);
        mPath.lineTo(0,0);
        mPath.lineTo(linelength/2,0);
        mPath.lineTo(linelength/2+linelength,0);
        mPath.lineTo(linelength/2+linelength+linelength,0);
        int i = mWith / linelength - linelength / 2;
        if(linelength>i){
            mPath.lineTo(linelength/2+linelength+linelength,0);
        }*/
        //  canvas.drawRect(mRf,mLinepaint);
        //canvas.drawRoundRect(mRf,rectfangle,rectfangle,mLinepaint);

       canvas.drawPath(setrevolve(),mLinepaint);
        handler.postDelayed(runnable,500);
    }

  private Path setrevolve(){
      Path mPath = new Path();

      int i = 2 * (mWith + mHeight);
      int i1 = i / linelength;
      int i2 = i % linelength;


    if(position==0&&!isone){
        mPath.moveTo(0,linelength/2);
        mPath.lineTo(0,0);
        mPath.lineTo(linelength/2,0);
        lastx = linelength/2;
        lasty = 0;
        position = 0;
        isone = true;
        return mPath;
    }
        mPath.moveTo(lastx,lasty);
      Log.e("LYH","lastx  "+lastx);
      Log.e("LYH","lasty  "+lasty);
     // Log.e("LYH","lasty  "+lasty);
        if(position==0){// 上面一条线轨迹
            Log.e("LYH","linelength"+linelength);
            if(linelength>=mWith-lastx){//转弯

                int i3 = mHeight - (linelength-(mWith-lastx));
                mPath.lineTo(mWith,0);
                mPath.lineTo(mWith,linelength-i3);
                lastx = mWith;
                lasty = linelength-i3;
                position =1;
                return mPath;
            }else {
                mPath.lineTo(lastx+linelength,0);
                lastx = lastx+linelength;
                lasty = 0;
                return mPath;
            }
        }

     if(position==1){//右边轨道
         Log.e("LYH","position==1");
         int i3 = mHeight - lasty;//得到左边轨道初始剩下的长度
         int i4 = i3 - linelength;//
         if(i3<linelength){  //当不足时执行拐弯
             int i5 = mHeight - lasty;
             int i6 = mWith - (linelength - i5);
             mPath.lineTo(lastx,mHeight);
             mPath.lineTo(i6,mHeight);
             lastx = i6;
             lasty = mHeight;
             position =2;
             return mPath;
         }else {
             mPath.lineTo(lastx,lasty+linelength);
             lastx = lastx;
             lasty = lasty+linelength;
             return mPath;
         }
         }

     if(position==2){//下边轨道
         int i3 = mWith - lastx;
         Log.e("LYH","position==2");
         if(lastx<linelength){  //转弯
             int i4 = linelength - lastx;
             mPath.lineTo(0,mHeight);
             mPath.lineTo(0,mHeight-i4);

             lastx = 0;
             lasty = mHeight-i4;
             position =3;
             return mPath;
         }else {

                mPath.lineTo(lastx-linelength,mHeight);
                lastx = lastx-linelength;
                lasty = mHeight;

                return mPath;



         }




     }
     if(position==3){//左边轨道
         Log.e("LYH","position==3");
         int i3 = mHeight - lasty;
         if(lasty<linelength){//拐弯
             mPath.lineTo(0,0);
             mPath.lineTo(linelength-lasty,0);

             lastx = linelength-lasty;
             lasty = 0;
             position =0;
             return mPath;
       }else {
             mPath.lineTo(0,lasty+linelength);
             lastx = 0;
             lasty = lasty+linelength;
             return mPath;

         }

     }



    return mPath;
  }
}
