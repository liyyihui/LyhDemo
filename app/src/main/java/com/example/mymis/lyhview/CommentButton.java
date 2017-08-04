package com.example.mymis.lyhview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 作者：LYH   2017/7/28
 * <p>
 * 邮箱：945131558@qq.com
 */

public class CommentButton extends LinearLayout {
    private int width;//宽
    private int height;//高
    private Paint mpaint;//画笔
    private Bitmap bitmap;//设置的图片
    private ImageView src;
    private Context mcontext;
   private boolean ison;
    private ValueAnimator animatorBack; //背景拉伸动画
    public CommentButton(Context context) {
        this(context,null);
    }

    public CommentButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CommentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
         initpaint();
        getdata(context,attrs);
    }

    private void getdata(Context context, AttributeSet attrs) {
        src = new ImageView(context);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommentButton);
        Drawable drawable = array.getDrawable(R.styleable.CommentButton_mbitmap);
        BitmapDrawable bt = (BitmapDrawable) drawable;
        bitmap = bt.getBitmap();

        array.recycle();
        addView(src);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(mcontext,"点击",Toast.LENGTH_SHORT).show();
              ScaleAnimation scaleAnimation=new ScaleAnimation(0,height,0,20);//默认从（0,0）
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                            Log.e("LYH","onAnimationStart");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.e("LYH","onAnimationEnd");
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        Log.e("LYH","onAnimationRepeat");
                    }
                });

                scaleAnimation.setDuration(3000);
                this.startAnimation(scaleAnimation);
                //左右移动
               // ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
               // animator.setRepeatMode(ObjectAnimator.RESTART);
                // animator.setRepeatCount(1);
               // animator.setDuration(1500);
               // animator.start();


                break;
        }

        return super.onTouchEvent(event);
    }

    /**
        * 初始化笔
        * */
    private void initpaint() {
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setColor(Color.parseColor("#fdfff700"));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(width/2,height/2,width/2,mpaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){

            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
            if(src!=null&&bitmap!=null&&!ison){
                ison = true;
                src.setImageBitmap(zoomImage(bitmap,width,height));

            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }




    private Bitmap zoomImage(Bitmap bgimage, double newWidth,
                             double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }
}
