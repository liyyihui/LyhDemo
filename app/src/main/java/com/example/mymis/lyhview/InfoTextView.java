package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者：LYH   2017/8/1
 * <p>
 * 邮箱：945131558@qq.com
 */

public class InfoTextView extends android.support.v7.widget.AppCompatTextView {
    private TextPaint mTextPaint;
    private Paint mShowTextPaint;
    private String mShowtext,mText,mHidetext;
   private int mShowend,mPaintcolor,width,height,type;
    private float showx,showy;
    private Context mcontext;
    StaticLayout mStaiclayout;


    public InfoTextView(Context context) {
        this(context,null);
    }

    public InfoTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InfoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        initdata(context,attrs);
        init();

    }

    private void initdata(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoTextView);
        mShowend = array.getInt(R.styleable.InfoTextView_showend,20);
        mShowtext =  array.getString(R.styleable.InfoTextView_mtext);
        mHidetext = array.getString(R.styleable.InfoTextView_mhidetext);
        mPaintcolor = array.getColor(R.styleable.InfoTextView_mtextcolor, Color.parseColor("#FF0022FF"));
       if (TextUtils.isEmpty(mShowtext))
           mShowtext = "点击展开";
        if(TextUtils.isEmpty(mHidetext)){
            mHidetext = "点击收缩";
        }
           array.recycle();
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint.setDither(true);//防抖动
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getCurrentTextColor());

        mShowTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShowTextPaint.setDither(true);//防抖动
        mShowTextPaint.setTextSize(getTextSize());
        mShowTextPaint.setColor(mPaintcolor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
     canvastext(canvas,type);
    }
      /**
       * 绘制方式
       * */
    private void canvastext(Canvas canvas,int type) {

       switch (type){
           case 0:
           case 1:
               ViewGroup.LayoutParams params1 = getLayoutParams();
               params1.width = width;
               params1.height = (int) (gettextwidth(getText().toString().substring(0,mShowend))%width!=0?((gettextwidth(getText().toString().substring(0,mShowend))/width)+2)*gettextheight()-3:(gettextwidth(getText().toString().substring(0,mShowend))/width+1)*gettextheight()-3);
               setLayoutParams(params1);

               canvastype1(canvas);
               break;
           case 2:

               canvastype2(canvas);
               break;
       }
    }
  /**
   * 未展开绘制
   * */
  private final void canvastype1(Canvas canvas){
      if(getText().toString().length()<20){
          canvas.drawText(getText().toString(),getPaddingLeft(),gettextheight(),mTextPaint);
      }else {
          mText = getText().toString().substring(0, mShowend);
          mStaiclayout = new StaticLayout(mText,mTextPaint,width, Layout.Alignment.ALIGN_NORMAL,1.0f,1.0f,false);
          mStaiclayout.draw(canvas);
          showx = width-getPaddingLeft()-(gettextwidth(mShowtext)+60);
          showy = height-10;
          canvas.drawText(mShowtext,showx,showy,mShowTextPaint);
          canvas.restore();


      }
  }

  /**
   * 展开内容的绘制
   * */
  private final void canvastype2(Canvas canvas){
      mText = getText().toString();
      mStaiclayout = new StaticLayout(mText,mTextPaint,width, Layout.Alignment.ALIGN_NORMAL,1.0f,1.0f,false);
      mStaiclayout.draw(canvas);
      showx = width-getPaddingLeft()-(gettextwidth(mShowtext)+60);
      showy = height-10;

      canvas.drawText(mHidetext,showx,showy,mShowTextPaint);

      canvas.restore();


  }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

    switch (event.getAction()){
        case MotionEvent.ACTION_DOWN:
            float x = event.getX();
            float y = event.getY();

            if((x>=showx&&x<=showx+(gettextwidth(mShowtext)))&&(y>=showy&&y<=showy+(gettextheight()))){
                //Toast.makeText(mcontext,"点击展开",Toast.LENGTH_SHORT).show();
                  switch (type){
                      case 0:
                      case 1:
                          type = 2;
                          ViewGroup.LayoutParams params1 = getLayoutParams();
                          params1.width = width;
                          params1.height = (int) (gettextwidth(getText().toString())%width!=0?(gettextwidth(getText().toString())/width+2)*gettextheight()+30:(gettextwidth(getText().toString())/width+1)*gettextheight()+30);
                          setLayoutParams(params1);
                          break;
                      case 2:
                          type = 1;
                         invalidate();
                          break;
                  }


            }
            break;
    }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        width = getWidth();
        height = getHeight();



        super.onLayout(changed, left, top, right, bottom);
    }

    private float gettextheight(){
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }


    /**
     * 文字宽度
     * */
    private float gettextwidth(String arr){
        return mTextPaint.measureText(arr);
    }


   /**
    * 获取文字的高度
    * */
    public int getFontHeight()
    {
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }
}
