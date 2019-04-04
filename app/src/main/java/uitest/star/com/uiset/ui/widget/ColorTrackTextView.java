package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2018/12/19.
 */

public class ColorTrackTextView extends AppCompatTextView {

    private int mOriginColor = Color.BLACK;
    private int mChangeColor = Color.RED;
    /*
    两只画笔
     */

    private Paint mOriginPaint;
    private Paint mChangePaint;

    private float mCurrentProgress = 0.0f;


    //实现不同朝向

    public enum Direction {
        LEFT_TORIGHT, RIGHT_TOLEFT
    }

    private Direction mDirection = Direction.LEFT_TORIGHT;


    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mChangeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, mChangeColor);
        mOriginColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, mOriginColor);


        mOriginPaint = getPaintByColor(mOriginColor);
        mChangePaint = getPaintByColor(mChangeColor);
        typedArray.recycle();
    }

    /**
     * 初始化 画笔
     *
     * @param color
     * @return
     */
    public Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setTextSize(getTextSize());
        paint.setDither(true);   //防止抖动
        return paint;
    }


    /**
     * 一个文字两种颜色
     * <p>
     * 利用clcpRect API, 左边一个画笔,右边一个画笔  不断改变中间值
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);


        //算出中间值
        int middle = (int) (mCurrentProgress * getWidth());


        //从左到右
        if (mDirection == Direction.LEFT_TORIGHT) {  //左边是红色右边是黑色

            //绘制变色
            drawText(canvas, mChangePaint, 0, middle);

            drawText(canvas, mOriginPaint, middle, getWidth());


        } else {
            //右边红色，左边黑色
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());

            drawText(canvas, mOriginPaint, 0, getWidth() - middle);

        }


    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {

        canvas.save();
        //绘制不变色的
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        //开始绘画
        String text = getText().toString();
        //获取这个textview尺寸
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        //获取字体宽度
        int x = getWidth() / 2 - bounds.width() / 2;

        //算出基线
        Paint.FontMetricsInt metricsInt = mChangePaint.getFontMetricsInt();
        int dy = (metricsInt.bottom - metricsInt.top) / 2 - metricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    /**
     * 设置方向
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;

    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        mCurrentProgress = progress;
        invalidate();

    }


}
