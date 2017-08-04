package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * 作者：LYH   2017/8/4
 * <p>
 * 邮箱：18081745066@163.com
 */

public class VerifyCode extends View {
    private int mPointNum;//干扰点数
    private int mLineNum;//干扰线数
    private static int mWidth;//控件的宽度
    private static int mHeight;//控件的高度
    private boolean isallnum;//验证码是否全数字
    private Paint mPaint;//画笔
    private Bitmap bitmap;//验证码图片
    private String mCodeText;//验证码文字
    private int mCodeBackground;//验证码背景颜色
    private  Random mRandom = new Random();
    private int mCodeTextSize;//验证码文字字体大小
    private Rect mBound;//绘制范围
    private int mCodeLength;//验证码长度
    public VerifyCode(Context context) {
        this(context,null);
    }

    public VerifyCode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerifyCode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerifyCode);
        mCodeTextSize =  array.getDimensionPixelSize(R.styleable.VerifyCode_codetextwidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
        mCodeBackground = array.getColor(R.styleable.VerifyCode_mCodeBackground, Color.parseColor("#FFA3A3A3"));
        mLineNum = array.getInt(R.styleable.VerifyCode_linenum,3);
        mPointNum = array.getInt(R.styleable.VerifyCode_mpointnum,100);
        isallnum = array.getBoolean(R.styleable.VerifyCode_allnumber,true);
        mCodeLength = array.getInt(R.styleable.VerifyCode_mcodelength,4);
        array.recycle();
        mPaint =  new Paint(Paint.ANTI_ALIAS_FLAG);
        mBound=new Rect();
        mCodeText = getValidationCode(mCodeLength,isallnum);
        //计算文字所在矩形，可以得到宽高
        mPaint.getTextBounds(mCodeText,0, mCodeText.length(),mBound);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth=getWidth();
        mHeight=getHeight();

        if (bitmap==null){
            bitmap=createBitmapValidate();
        }
        canvas.drawBitmap(bitmap,0,0,mPaint);

    }

    /**
     * 创建图片验证码
     * @return
     */
    private Bitmap createBitmapValidate(){
        if(bitmap != null && !bitmap.isRecycled()){
            //回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        //创建图片
        Bitmap sourceBitmap=Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas=new Canvas(sourceBitmap);
        //画上背景颜色
        canvas.drawColor(mCodeBackground);
        //初始化文字画笔
        mPaint.setStrokeWidth(3f);
        mPaint.setTextSize(mCodeTextSize);
        //测量验证码字符串显示的宽度值
        float textWidth=mPaint.measureText(mCodeText);
        //画上验证码
        int length = mCodeText.length();
        //计算一个字符的所占位置
        float charLength = textWidth / length;
        for (int i = 1; i <= length; i++) {
            int offsetDegree = mRandom.nextInt(15);
            //这里只会产生0和1，如果是1那么正旋转正角度，否则旋转负角度
            offsetDegree = mRandom.nextInt(2) == 1 ? offsetDegree : -offsetDegree;
            //用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
            canvas.save();
            //设置旋转
            canvas.rotate(offsetDegree, mWidth / 2, mHeight / 2);
            //给画笔设置随机颜色
            mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                    mRandom.nextInt(200) + 20);
            //设置字体的绘制位置
            canvas.drawText(String.valueOf(mCodeText.charAt(i - 1)), (i - 1) * charLength+5,
                    mHeight * 4 / 5f, mPaint);
            //用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
            canvas.restore();
        }

        //重新设置画笔
        mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                mRandom.nextInt(200) + 20);
        mPaint.setStrokeWidth(1);
        //产生干扰效果1 －－ 干扰点
        for (int i = 0; i < mPointNum; i++) {
            drawPoint(canvas, mPaint);
        }
        //生成干扰效果2 －－ 干扰线
        for (int i = 0; i < mLineNum; i++) {
            drawLine(canvas, mPaint);
        }
        return sourceBitmap;
    }

    /**
     * 生成干扰点
     */
    private  void drawPoint(Canvas canvas, Paint paint) {
        PointF pointF = new PointF(mRandom.nextInt(mWidth) + 10, mRandom.nextInt(mHeight) + 10);
        canvas.drawPoint(pointF.x, pointF.y, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                refresh();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 生成干扰线
     */
    private  void drawLine(Canvas canvas, Paint paint) {
        int startX = mRandom.nextInt(mWidth);
        int startY = mRandom.nextInt(mHeight);
        int endX = mRandom.nextInt(mWidth);
        int endY = mRandom.nextInt(mHeight);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    /**
     *判断验证码是否一致 忽略大小写
     */
    public Boolean isEqualsIgnoreCase(String CodeString) {
        return mCodeText.equalsIgnoreCase(CodeString);
    }

    /**
     * 判断验证码是否一致 忽略大小写
     */
    public Boolean isEquals(String CodeString) {
        return mCodeText.equalsIgnoreCase(CodeString);
    }

    /**
     * 提供外部调用的刷新方法
     */
    public void refresh(){
        mCodeText= getValidationCode(mCodeLength,isallnum);
        bitmap = createBitmapValidate();
        invalidate();
    }


  /**
   * 生成验证文字
   * */
    public String getValidationCode(int length,boolean contains) {
        String val = "";
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            if (contains){
                //字母或数字
                String code = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //字符串
                if ("char".equalsIgnoreCase(code)) {
                    //大写或小写字母
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(code)) {
                    val += String.valueOf(random.nextInt(10));
                }
            }else{
                val += String.valueOf(random.nextInt(10));
            }

        }
        return val;
    }
}
