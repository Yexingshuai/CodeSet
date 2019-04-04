package uitest.star.com.uiset.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import uitest.star.com.uiset.R;

/**
 * Created by yexing  on 2019/1/7.
 */

/**
 * 倒计时View
 */
public class CountDownView extends View implements View.OnClickListener {

    public static final int MDEFAULT_ANIM_TIME = 3;
    private Paint mTextPaint;
    private Paint mOriginPaint;
    private Paint mDrawPaint;
    private float mCurrentProgress;
    private TimeListener listener;
    private int mOuterColor;
    private int mInnerColor;
    private int mAnimTime;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mOuterColor = typedArray.getColor(R.styleable.CountDownView_outerColor1, getResources().getColor(R.color.gray));
        mInnerColor = typedArray.getColor(R.styleable.CountDownView_innerColor1, getResources().getColor(R.color.red));
        mAnimTime = typedArray.getInt(R.styleable.CountDownView_time, MDEFAULT_ANIM_TIME);
        if (mAnimTime <= 0) {
            throw new RuntimeException("动画时间必须大于0s!");
        }
        typedArray.recycle();

        mOriginPaint = new Paint();
        mOriginPaint.setStrokeWidth(14);
        mOriginPaint.setAntiAlias(true);
        mOriginPaint.setStyle(Paint.Style.STROKE);
        mOriginPaint.setColor(mOuterColor);

        mDrawPaint = new Paint();
        mDrawPaint.setStrokeWidth(14);
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setColor(mInnerColor);


        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.colorBlack));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(sp2px(16));

        this.setOnClickListener(this);
        initAnim();

    }

    public float sp2px(int px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    private void initAnim() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mAnimTime, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(mAnimTime * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentProgress = (float) valueAnimator.getAnimatedValue();
                if (mCurrentProgress == 0 && listener != null) {
                    listener.end();
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当宽高不一致时，取最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? width : height, width > height ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float v = (mAnimTime - mCurrentProgress) / mAnimTime * 360;

        RectF rect = new RectF((float) (getWidth() * 0.05), (float) (getWidth() * 0.05), (float) (getWidth() * 0.95), (float) (getWidth() * 0.95));

        canvas.drawArc(rect, 0, 360, true, mOriginPaint); //原始
        canvas.drawArc(rect, 0, v, false, mDrawPaint);
        //drawText
        String text = (int) mCurrentProgress + 1 + "s";
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), textRect);
        int dx = getWidth() / 2 - textRect.width() / 2;
        //算出基线
        int dy = (textRect.bottom - textRect.top) / 2 - textRect.bottom;
        int baseLine = getHeight() / 2 + dy;
        if (mCurrentProgress == 0) {
            String text1 = (int) mCurrentProgress + "s";
            canvas.drawText(text1, dx, baseLine, mTextPaint);
        } else {
            canvas.drawText(text, dx, baseLine, mTextPaint);
        }

    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.click();
        }
    }

    public void setTimeListener(TimeListener listener) {
        this.listener = listener;
    }

    public interface TimeListener {

        void end();

        void click();
    }

}
