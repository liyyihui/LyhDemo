package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 作者：LYH   2017/7/27
 * <p>
 * 邮箱：945131558@qq.com
 */

public class LineGraphView extends View {
    public static final int WEEK = 1;//周
    public static final int MOON = 2;//月
    public static final int YEAR = 3;//年
    private int arr[];
    private double weekarr[];

    private Paint mLinepaint;//画线条笔
    private Paint mTextpaint;//刻度文字笔
    private Paint mDatapaint;//数据线条笔
    private Paint mCirclepaint;//数据线条连接点
    private int mLinepaintcolor,mTextpaintcolor,mDatapaintolor,mCirclepaintcolor;//颜色
    private int width,height;
    private float oneweekheight,zweekheight,oneweekwidth,zweekwidth;

    private float mLinepaintwidth,mTextpaintwidth,mDatapaintwidth;
    public LineGraphView(Context context) {

        this(context,null);
    }

    public LineGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getattrdata(context,attrs);
        initpaint();
    }
    /*
    * 初始化画笔
    * */
    private void initpaint() {
        mLinepaint = new Paint(Paint.ANTI_ALIAS_FLAG);//此种方式构造直接就是抗锯齿效果
        mTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);//此种方式构造直接就是抗锯齿效果
        mDatapaint = new Paint(Paint.ANTI_ALIAS_FLAG);//此种方式构造直接就是抗锯齿效果
        mCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);//此种方式构造直接就是抗锯齿效果
        mLinepaint.setStyle(Paint.Style.STROKE);
        mTextpaint.setStyle(Paint.Style.STROKE);
        mDatapaint.setStyle(Paint.Style.STROKE);
        mCirclepaint.setStyle(Paint.Style.FILL);
        mLinepaint.setStrokeWidth(mLinepaintwidth);
        mTextpaint.setStrokeWidth(mTextpaintwidth);
        mDatapaint.setStrokeWidth(mDatapaintwidth);
        mLinepaint.setColor(mLinepaintcolor);
        mTextpaint.setColor(mTextpaintcolor);
        mDatapaint.setColor(mDatapaintolor);
        mCirclepaint.setColor(mCirclepaintcolor);
    }

    /*
    * 获取自定义数据
    * */
    private void getattrdata(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineGraphView);
        mLinepaintcolor = array.getColor(R.styleable.LineGraphView_linepaint, Color.parseColor("#FF6C6B6B"));
        mTextpaintcolor = array.getColor(R.styleable.LineGraphView_textpaintcolor, Color.parseColor("#FF000000"));
        mDatapaintolor = array.getColor(R.styleable.LineGraphView_datapaintcolor, Color.parseColor("#0011ff"));
        mCirclepaintcolor = array.getColor(R.styleable.LineGraphView_circlepaintcolor, Color.parseColor("#ff0000"));
        mLinepaintwidth = array.getDimension(R.styleable.LineGraphView_linepaintwidth, 4);
        mTextpaintwidth =  array.getDimension(R.styleable.LineGraphView_textpaintwidth,2);
        mDatapaintwidth = array.getDimension(R.styleable.LineGraphView_datapaintwidth,1);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
            oneweekheight = (float) ((height - gettextheight()-gettextheight())/5);
            zweekheight = (float) (height - gettextheight());
            zweekwidth = width-gettextwidth("000");
            oneweekwidth = zweekwidth/7;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  private float gettextwidth(String arr){
     return mTextpaint.measureText(arr);
  }

    //设置自定义周刻度
    public void setArr(int[] arr) {
        invalidate();
        this.arr = arr;
    }
    //设置自定义周数据
    public void setWeekarr(double[] weekarr) {

            this.weekarr = weekarr;


    }

    private double gettextheight(){
      Paint.FontMetrics fm = mTextpaint.getFontMetrics();
     return Math.ceil(fm.descent - fm.ascent);
  }
    @Override
    protected void onDraw(Canvas canvas) {
        //竖线
         canvas.drawLine(0+gettextwidth("000"),0,0+gettextwidth("000"),(float) (height-gettextheight()),mLinepaint);
        //横线
         canvas.drawLine(0+gettextwidth("000"),(float) (height-gettextheight()),width,(float) (height-gettextheight()),mLinepaint);


        canvasform(WEEK,canvas);


        super.onDraw(canvas);
    }

    private void canvasform(int type,Canvas canvas) {
        switch (type){
            case WEEK: //周绘制
                canvasweek(canvas);
                break;
            case MOON://月绘制
                break;
            case YEAR://年绘制
                break;
        }
    }
    /**
     * 周绘制
     * */
    private final void canvasweek(Canvas canvas){
        float x;
        if(arr!=null&&weekarr!=null){//自定义刻度
            oneweekheight = (float) ((height - gettextheight()-gettextheight())/arr.length);
            for(int i = 0;i<arr.length;i++){
                canvas.drawText(arr[i]+"", x = arr[i]>99?0:gettextwidth("0"),zweekheight-(oneweekheight*(i+1)),mTextpaint);
            }
            //横线刻度
            canvas.drawText("一",oneweekwidth,height,mTextpaint);
            canvas.drawText("二",oneweekwidth*2,height,mTextpaint);
            canvas.drawText("三",oneweekwidth*3,height,mTextpaint);
            canvas.drawText("四",oneweekwidth*4,height,mTextpaint);
            canvas.drawText("五",oneweekwidth*5,height,mTextpaint);
            canvas.drawText("六",oneweekwidth*6,height,mTextpaint);
            canvas.drawText("日",oneweekwidth*7,height,mTextpaint);
            //数据绘制
            Path path = new Path();
            path.moveTo(gettextwidth("000"),(float) (height-gettextheight()));
            float y = oneweekheight / (arr[1] - arr[0]);

            for(int a = 0;a<weekarr.length;a++){
             if(a<=6){
                 path.lineTo(oneweekwidth*(a+1), (float) ((height-gettextheight())-(weekarr[a]*y)));
                 canvas.drawCircle(oneweekwidth*(a+1), (float) ((height-gettextheight())-(weekarr[a]*y)),5,mCirclepaint);
             }

           }
           canvas.drawPath(path,mDatapaint);
        }else {//默认刻度

            //竖线刻度
            canvas.drawText(" 50",0,zweekheight-oneweekheight,mTextpaint);
            canvas.drawText("100",0,zweekheight-(oneweekheight*2),mTextpaint);
            canvas.drawText("150",0,zweekheight-(oneweekheight*3),mTextpaint);
            canvas.drawText("250",0,zweekheight-(oneweekheight*4),mTextpaint);
            canvas.drawText("300",0,zweekheight-(oneweekheight*5),mTextpaint);
            //横线刻度
            canvas.drawText("一",oneweekwidth,height,mTextpaint);
            canvas.drawText("二",oneweekwidth*2,height,mTextpaint);
            canvas.drawText("三",oneweekwidth*3,height,mTextpaint);
            canvas.drawText("四",oneweekwidth*4,height,mTextpaint);
            canvas.drawText("五",oneweekwidth*5,height,mTextpaint);
            canvas.drawText("六",oneweekwidth*6,height,mTextpaint);
            canvas.drawText("日",oneweekwidth*7,height,mTextpaint);

            //数据线条
            canvas.drawLine(gettextwidth("000"),(float) (height-gettextheight()),oneweekwidth,zweekheight-oneweekheight,mDatapaint);
            canvas.drawLine(oneweekwidth,zweekheight-oneweekheight,oneweekwidth*2,zweekheight-oneweekheight,mDatapaint);
            canvas.drawLine(oneweekwidth*2,zweekheight-oneweekheight,oneweekwidth*3,zweekheight-(oneweekheight*2),mDatapaint);
        }

    }

    /**
     * 月绘制
     * */
    private final void canvasmoon(Canvas canvas){

    }
}
