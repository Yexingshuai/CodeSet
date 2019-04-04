package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yexing on 2018/12/27.
 */

public class LetterSideBar extends View {

    private Paint mPaint;
    private static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int itemHeight;
    private int touchNum;
    private LetterSideBarListener mLetterSideBarListener;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(sp2px(18));//要把他转成sp
    }

    public float sp2px(int px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度   左右的padding+字母的宽度 (字母宽度取决于画笔大小)

        int textWidth = (int) mPaint.measureText("M");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
        //高度

        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                touchNum = (int) (moveY / itemHeight);
                if (touchNum < 0) touchNum = 0;
                if (touchNum > mLetters.length - 1) touchNum = mLetters.length - 1;
                if (mLetterSideBarListener != null) {
                    mLetterSideBarListener.touchText(mLetters[touchNum]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mLetterSideBarListener != null) {
                    mLetterSideBarListener.dismiss();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // X 等于  宽度/2 -文字宽度/2
        itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;

        for (int i = 0; i < mLetters.length; i++) {
            //确定字母的中心位置， 1.字母高度一半 2.字母高度一半+ 前面字符的高度

            // X 等于  宽度/2 -文字宽度/2
            int x = getWidth() / 2 - (int) mPaint.measureText(mLetters[i]) / 2;
            int letterY = i * itemHeight + itemHeight / 2 + getPaddingTop();   //这个是中心位置
            //基线，基于中心位置

            Rect textRect = new Rect();
            mPaint.getTextBounds(mLetters[i], 0, 1, textRect);
            int dy = textRect.bottom;
            int baseLine = letterY + dy;
            if (i == touchNum) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawText(mLetters[i], x, baseLine, mPaint);
        }
    }

    /**
     * 计算绘制文字时的基线到中轴线的距离
     *
     * @param p
     * @param
     * @return 基线和centerY的距离
     */
    public static float getBaseline(Paint p) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }

    public void setLetterSideBarListener(LetterSideBarListener letterSideBarListener) {
        mLetterSideBarListener = letterSideBarListener;
    }


    public interface LetterSideBarListener {

        void touchText(String s);

        void dismiss();
    }


}
