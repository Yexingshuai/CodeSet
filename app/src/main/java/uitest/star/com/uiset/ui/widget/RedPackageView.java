package uitest.star.com.uiset.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/1/17.
 */

public class RedPackageView extends View {

    private Bitmap mRedPackageBitmap; //红包
    private Bitmap mProgressBgBitmap;

    private float mTotalProgress;
    private float mCureentProgress;

    private Paint mPaint;

    private int mProgressStarColor = Color.parseColor("#FDA501");
    private int mProgressEndColor = Color.parseColor("#FFEF74");

    private Bitmap[] mBombIcon = new Bitmap[8];

    private float mCurrentBombProgress;//当前爆炸的进度
    private int mBombNumber = 8;

    private float mTotalBombRadius=0;
    private Paint mBombPaint;

    public RedPackageView(Context context) {
        this(context, null);
    }

    public RedPackageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedPackageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRedPackageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_game_red_package_normal);
        mProgressBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_game_red_package_pb_bg);

        Bitmap bomb1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red_package_bomb_1);
        Bitmap bomb2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red_package_bomb_2);

        mTotalBombRadius = mProgressBgBitmap.getHeight() * 0.7f;
        mBombIcon[0] = bomb1;
        mBombIcon[1] = bomb2;

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mBombPaint = new Paint();
        mBombPaint.setDither(true);
        mBombPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //指定宽高
        int size = (int) Math.max(mRedPackageBitmap.getWidth(), mRedPackageBitmap.getHeight() * 1.1f);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        super.onDraw(canvas);
        //画红包
        canvas.drawBitmap(mRedPackageBitmap, 0, 0, null);
        //画背景  而且要适配   不要出现写死的值 dp,px之类
        int top = (int) (mRedPackageBitmap.getHeight() - mProgressBgBitmap.getHeight() * 0.8f);
        int left = (int) ((width - mProgressBgBitmap.getWidth()) / 2 - mProgressBgBitmap.getHeight() * 0.02f);
        canvas.drawBitmap(mProgressBgBitmap, left, top, null);

        if (mTotalProgress == 0 || mCureentProgress == 0) {
            return;
        }

        int bombCenterX = 0;
        int bombCenterY = 0;

        if (mCureentProgress != 0) {

            int progressHeight = (int) ((mProgressBgBitmap.getHeight()) * 0.35f);


            //画进度
            int progressWidth = (int) (mProgressBgBitmap.getWidth() * 0.78f);

            int currentProgressWidth = (int) (progressWidth * mCureentProgress / mTotalProgress);

            left = (int) (mProgressBgBitmap.getWidth() * 0.15f);
            top = (int) (height * 0.75f);

            RectF rectF = new RectF(left, top, left + currentProgressWidth, top + progressHeight);

            int round = progressHeight / 2;
            // 设置进度条渐变颜色
            Shader shader = new LinearGradient(0, 0, currentProgressWidth, 0, new int[]{mProgressStarColor, mProgressEndColor}, new float[]{0, 1.0f}, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawRoundRect(rectF, round, round, mPaint);   //圆角矩形
        }

        //爆炸的动画  画八个
        if (mCurrentBombProgress > 0 && mCurrentBombProgress < 1) {
            // 爆炸的动画，准备几个 Bitmap for循环，计算半径
            float preAngle = (float) (2 * Math.PI / mBombNumber);
            mBombPaint.setAlpha((int) (300 - mCurrentBombProgress * 255));// 0~255
            for (int i = 0; i < mBombNumber; i++) {
                // 初始角度 + 当前旋转的角度
                double angle = i * preAngle;
                float mCurrentBombRadius = mTotalBombRadius * mCurrentBombProgress;
                float cx = (float) (bombCenterX + mCurrentBombRadius * Math.cos(angle));
                float cy = (float) (bombCenterY + mCurrentBombRadius * Math.sin(angle));
                Bitmap bombBitmap = mBombIcon[i % 2];
                canvas.drawBitmap(bombBitmap, cx - bombBitmap.getWidth() / 2, cy, mBombPaint);
            }
        }
    }


    private synchronized void setCureentProgress(float cureentProgress) {
        this.mCureentProgress = cureentProgress;
        invalidate();
    }

    public void setTotalProgress(float totalProgress) {
        this.mTotalProgress = totalProgress;
    }

    public void startAnimation(int from, int to) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float current = (float) valueAnimator.getAnimatedValue();
                setCureentProgress(current);
            }
        });

        //进度条沾满之后，执行扩散爆炸
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                executeBobAnimator();
            }
        });
        valueAnimator.start();
    }

    /**
     * 执行扩散动画
     */
    private void executeBobAnimator() {

         ValueAnimator animtor = ValueAnimator.ofFloat(0, 1);
        animtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentBombProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animtor.start();


    }
}
