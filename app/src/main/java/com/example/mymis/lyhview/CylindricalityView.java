package com.example.mymis.lyhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者：LYH   2017/7/11
 * <p>
 * 邮箱：18081745066@163.com
 */

public class CylindricalityView extends View {
    private Paint mpaintline;//边线画笔
    private Bitmap bitmap;//设置的图片
    private int width;//宽
    private int height;//高
    public CylindricalityView(Context context) {
        super(context);
        initpaint();
    }

    private void initpaint() {
        if(mpaintline == null){
            mpaintline = new Paint();
            mpaintline.setAntiAlias(true);
            mpaintline.setStyle(Paint.Style.FILL); //实心

        }
    }

    public CylindricalityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initpaint();
        initbitmp(context,attrs);
    }

    private void initbitmp(Context context ,AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CylindricalityView);
        Drawable drawable = array.getDrawable(R.styleable.CylindricalityView_bitmap);
        BitmapDrawable bt = (BitmapDrawable) drawable;
        bitmap = bt.getBitmap();
       // bitmap = BitmapFactory.decodeResource(getResources(),R.styleable.CylindricalityView_bitmap);

        Log.e("LYH","bitmap"+(bitmap==null));
        Log.e("LYH","drawable"+(drawable==null));
       // Log.e("LYH","drawableID"+(R.styleable.CylindricalityView_bitmap));
        array.recycle();
    }

    public CylindricalityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initpaint();
        initbitmp(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){

            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      // Shader shader =  new SweepGradient(25, 25, Color.parseColor("#FFF40000"), Color.parseColor("#FF0073F5"));
      Shader shader = new BitmapShader(zoomImage(bitmap,width,height), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
       mpaintline.setShader(shader);
        canvas.drawCircle(width/2, height/2, width/2, mpaintline);
      //  canvas.drawBitmap(bitmap,25,25,mpaintline);
        super.onDraw(canvas);
    }

    private  Bitmap zoomImage(Bitmap bgimage, double newWidth,
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
