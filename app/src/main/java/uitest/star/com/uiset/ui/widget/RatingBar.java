package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2018/12/27.
 */

public class RatingBar extends View {
    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int starNum = 0;  //星星数量

    private int mCurrentStarNum = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int starNormalId = typedArray.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal");

        }
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        int starFocusId = typedArray.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starFocus");
        }
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);
        starNum = typedArray.getInt(R.styleable.RatingBar_gradeNumber, starNum);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度，星星的高度，一个就够
        //宽度，星星所有数量的宽度，和边距
        int height = mStarFocusBitmap.getHeight();
        int width = mStarFocusBitmap.getWidth() * starNum;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < starNum; i++) {
            int x = i * mStarFocusBitmap.getWidth();

            if (mCurrentStarNum > i) {
                canvas.drawBitmap(mStarFocusBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:

                float moveX = event.getX();  //相对于当前控件的位置
                int currentGrade = (int) (moveX / mStarFocusBitmap.getWidth() + 1);

                //指定范围
                if (currentGrade < 0) {
                    currentGrade = 0;
                }
                if (currentGrade > starNum) {
                    currentGrade = starNum;
                }

                mCurrentStarNum = currentGrade;
                invalidate();

                break;


        }
        return true;
    }
}
