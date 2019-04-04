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
import android.util.TypedValue;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2018/12/14.
 */

public class MyTextView extends AppCompatTextView {

    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    private Paint mPaint;


    //代码里使用
    public MyTextView(Context context) {
        this(context, null);
    }

    //布局中使用
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, -1);
    }

    //在布局中使用， 包含有style
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        //字体
        mText = typedArray.getString(R.styleable.MyTextView_text);
        mTextColor = typedArray.getColor(R.styleable.MyTextView_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_textSize, sp2px(mTextSize));
        //回收
        typedArray.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }

    /**
     * sp转化成px
     *
     * @param mTextSize
     * @return
     */
    private int sp2px(int mTextSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //如果宽度是确定的值，就不需要计算，给多少是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //,如果给的是wrap_content，就需要计算
        if (widthMode == MeasureSpec.AT_MOST) {
            //计算的宽度，与字体长度有关和字体大小，用画笔来测量.
            Rect rect = new Rect();
            //获取文本的Rect
            mPaint.getTextBounds(mText, 0, mText.length(), rect);
            width = rect.width() + getPaddingLeft() + getPaddingRight();
            height = rect.height() + getPaddingTop() + getPaddingBottom();
        }
        //设置控件 的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //x  开始的位置，  Y  基线   baseline
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();

        //top 是一个负值，bottom是一个正值， 分别代表是 文字的上部和底部 到基线的距离
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        //x轴应该是从左边距的位置

        canvas.drawText(mText, getPaddingLeft(), baseLine, mPaint);
    }
}
