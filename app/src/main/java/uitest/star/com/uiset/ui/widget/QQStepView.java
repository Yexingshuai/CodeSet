package uitest.star.com.uiset.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2018/12/18.
 */

public class QQStepView extends View {

    private int mBorderWidth = 20;
    private int mStepTextColor = Color.BLACK;
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mStepTextSize = 16;

    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mStepMax = 1000;

    private int mCurrentStep = 0;


    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        /*
              1.分析效果
              2.确定自定义属性 编写attrs
              3.在布局中使用
              4.在自定义View中获取自定义属性
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);

        mBorderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        mStepMax = typedArray.getColor(R.styleable.QQStepView_stepMax, mStepMax);
        mCurrentStep = typedArray.getColor(R.styleable.QQStepView_stepCurrent, mCurrentStep);

        typedArray.recycle();

        /*
              5.onMeasure();
              6.画外圆弧，内圆弧，文字
              7.其他.
         */

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);    //画笔不是实心的

        mInnerPaint = new Paint();
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeWidth(mBorderWidth);


        mTextPaint = new Paint();
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mStepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中 可能是wrap_content ,也有可能宽高不一致

        //当宽高不一致时，取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? width : height, width > height ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //6.1  画外圆弧


        int center = getWidth() / 2;
        //半径
        int radius = center - mBorderWidth;


        RectF rect = new RectF(center - radius, center - radius, center + radius, center + radius);

        Log.e("xxx---", center - radius + "");
        Log.e("xxx---", center + radius + "");

        //中间的boolean  参数  意思是： 是否连接到中心
        canvas.drawArc(rect, 135, 270, false, mOutPaint);


        //6.2　画内圆弧  百分比，使用者从外界传入
        if (mStepMax == 0) return;
        //先算出比例
        float sweepAngle = (float) mCurrentStep / mStepMax;
        canvas.drawArc(rect, 135, sweepAngle * 270, false, mInnerPaint);

        //6.3  画字
        String text = mCurrentStep + "";
        Rect rectText = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rectText);
        int dx = getWidth() / 2 - rectText.width() / 2;
        //算出基线
        int dy = (rectText.bottom - rectText.top) / 2 - rectText.bottom;

        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, mTextPaint);

    }

    /**
     * 设置当前步数
     *
     * @param currentStep
     */
    public void setCurrentStep(int currentStep) {
//        mCurrentStep = currentStep;
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, currentStep);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mCurrentStep = (int) animatedValue;
                invalidate();
            }
        });
        valueAnimator.start();

    }

    /**
     * 设置总步数
     *
     * @param stepMax
     */
    public void setStepMax(int stepMax) {
        mStepMax = stepMax;
    }
}
