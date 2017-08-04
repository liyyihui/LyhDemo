package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

/**
 * 作者：LYH   2017/7/20
 * <p>
 * 邮箱：945131558@qq.com
 */

public class CircularProgressView extends ProgressBar {
    private Paint mLinepaint;//画线条笔
    private Paint mProgresss;//进度画笔
    private Paint mTextpaint;//写文字笔
    private int width;//宽
    private int height;//高
    private double  girth;//周长
    private double max;
    private double progress;
    private int paintwidth;
    private  RectF oval;//矩形
    private  boolean isopen;
    private int paintcolor,paintstartcolor,paintendcolor,painttextcolor;
    private float textheight;
    public CircularProgressView(Context context) {
        this(context,null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getattrscolor(context,attrs);
        initpaint();
    }

    private void getattrscolor(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
        paintcolor = array.getColor(R.styleable.CircularProgressView_paintcolor, Color.parseColor("#FFFF0000"));
       paintstartcolor = array.getColor(R.styleable.CircularProgressView_satrtcolor,Color.parseColor("#FFFF0000"));
       paintendcolor = array.getColor(R.styleable.CircularProgressView_endcolor,Color.parseColor("#220015FF"));
        painttextcolor = array.getColor(R.styleable.CircularProgressView_textcolor,Color.parseColor("#FFFF0000"));
        isopen = array.getBoolean(R.styleable.CircularProgressView_opengradualchange,false);
        array.recycle();
    }

    private void initpaint() {
        paintwidth = 15;
        mProgresss = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinepaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mLinepaint.setStyle(Paint.Style.STROKE);
        mProgresss.setStyle(Paint.Style.STROKE);
          if(isopen){//开启渐变
              Shader shader = new LinearGradient(width/2,height/2,width/2,height/2, paintstartcolor,
                     paintendcolor,Shader.TileMode.CLAMP);
              mProgresss.setShader(shader);
         }else {  //不开渐变

              mProgresss.setColor(paintcolor);
                }
        mProgresss.setStrokeCap(Paint.Cap.ROUND);//画笔线条头子类型
        mProgresss.setStrokeWidth(paintwidth);
        mTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextpaint.setColor(painttextcolor);
        textheight = mTextpaint.measureText("7%");

        // textheight = metrics.bottom - metrics.ascent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){

            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
            girth = ((width-15)/2)*2*3.14;
             oval =new RectF(0+(paintwidth/2),0+(paintwidth/2),width-(paintwidth/2),height-(paintwidth/2));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(width/2, height/2, width/2, mLinepaint);
        canvas.drawCircle(width/2, height/2, (width-30)/2, mLinepaint);
       //canvas.drawCircle(width/2, height/2, (width-15)/2, mProgresss);
        canvas.drawArc(oval,180, (float) progress,false,mProgresss);

        canvas.drawText(percentum()+"%",((width/2)-(textheight/2)),(height/2),mTextpaint);

        super.onDraw(canvas);
    }

    public void setmProgresssMax(double max) {
        this.max = max;
    }

    public void setmProgresss(double progress) {
        if(max<=0){
            max = 100;
        }
        this.progress = (progress*360)/max;
        textheight = mTextpaint.measureText(percentum());
        invalidate();
    }

    private String percentum(){
        String per = "0";
       String mper =  (progress/360*100)+"";
       // Log.e("LYH","进度"+mper);
        if(((progress/360*100)>=99)){
            return "100";
        }
        try {
            per = mper.substring(0,mper.indexOf("."));
        } catch (Exception e) {
            e.printStackTrace();
            return (progress/360*100)+"";
        }
        return per;
    }

}
