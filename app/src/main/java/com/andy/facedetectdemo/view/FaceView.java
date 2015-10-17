package com.andy.facedetectdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.andy.facedetectdemo.R;

/**
 * 用于显示图片以及识别面部信息后画出的面部轮廓
 * Created by lxn on 2015/10/17.
 */
public class FaceView extends View {

    private Bitmap image;
    private Paint paint;
    private RectF profile;
    private String info = "";

    private int imgWidth;
    private int imgHeight;

    public FaceView(Context context) {
        this(context, null);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(25);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        profile = new RectF();
    }

    /**
     * 重绘视图
     */
    public void refresh() {
        invalidate();
    }

    /**
     * 设置识别出来的面部轮廓区域
     * @param rect 轮廓区域
     */
    public void setProfile(RectF rect) {
        this.profile = rect;
    }


    /**
     * 设置显示的图片
     * @param bitmap 要识别的图片
     */
    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
    }

    /**
     * 设置识别出来的信息，如年龄，性别等
     * @param info 识别出来的信息
     */
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        imgWidth = getMeasuredWidth();
        imgHeight = getHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.outWidth = getWidth();
        options.outHeight = 500;
        image = BitmapFactory.decodeResource(getResources(), R.drawable.image, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImage(canvas);
        drawProfile(canvas);
        drawText(canvas);
    }

    private void drawImage(Canvas canvas) {
        canvas.drawBitmap(image, 10,10, paint);
    }

    private void drawProfile(Canvas canvas) {

        imgWidth = image.getWidth();
        imgHeight = image.getHeight();
        int left = (int)(profile.left*imgWidth/100);
        int top = (int)(profile.top*imgHeight/100);
        int right = (int)(profile.right*imgWidth/100);
        int bottom = (int)(profile.bottom*imgHeight/100);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint);
    }

    private void drawText(Canvas canvas) {
        int left = (int)(profile.left*imgWidth/100 + 10);
        int top = (int)(profile.top*imgHeight/100 + 30);
        canvas.drawText(info, left, top, paint);
    }

}
