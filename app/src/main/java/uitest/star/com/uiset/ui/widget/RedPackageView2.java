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


public class RedPackageView2 extends View {

    private Bitmap mRedPackageBitmap;// 红包
    private Bitmap mProgressBgBitmap;// 进度条背景
    // 进度定义
    private float mTotalProgress = 2;
    private float mCurrentProgress = 1;
    // 颜色渐变
    private int mProgressStarColor = Color.parseColor("#FDA501");
    private int mProgressEndColor = Color.parseColor("#FFEF74");

    private Paint mProgressPaint;
    // 爆炸的个数
    private int mBombNumber = 8;
    // 爆炸的icon
    private Bitmap[] mBombIcon = new Bitmap[8];
    // 爆炸最大半径
    private float mTotalBombRadius = 0;

    private float mCurrentBombProgress;// 当前爆炸的进度
    private Paint mBombPaint;

    private ValueAnimator mProgressAnimator;

    public RedPackageView2(Context context) {
        this(context, null);
    }

    public RedPackageView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedPackageView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRedPackageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_game_red_package_normal);
        mProgressBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_game_red_package_pb_bg);

        mTotalBombRadius = mProgressBgBitmap.getHeight() * 0.7f;

        Bitmap bomb1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red_package_bomb_1);
        Bitmap bomb2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red_package_bomb_2);

        mBombIcon[0] = bomb1;
        mBombIcon[1] = bomb2;

        mProgressPaint = new Paint();
        mProgressPaint.setDither(true);
        mProgressPaint.setAntiAlias(true);

        mBombPaint = new Paint();
        mBombPaint.setDither(true);
        mBombPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 指定宽高? 为了适配爆炸效果
        int size = (int) (Math.max(mRedPackageBitmap.getWidth(), mRedPackageBitmap.getHeight()) * 1.2f);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        // 画红包
        canvas.drawBitmap(mRedPackageBitmap, 0, 0, null);
        // 画背景 调整（适配，源码的熟悉）算比例
        int top = (int) (mRedPackageBitmap.getHeight() - mProgressBgBitmap.getHeight() * 0.8f);
        int left = (int) (mProgressBgBitmap.getWidth() * 0.08f);
        canvas.drawBitmap(mProgressBgBitmap, left, top, null);

        if (mTotalProgress == 0) {
            return;
        }

        int bombCenterX = 0;
        int bombCenterY = 0;
        if (mCurrentProgress != 0) {
            // 画进度 totalProgress currentProgress
            int progressWidth = (int) (mProgressBgBitmap.getWidth() * 0.78f);
            int currentProgressWidth = (int) (progressWidth * mCurrentProgress / mTotalProgress);
            int progressHeight = (int) (mProgressBgBitmap.getHeight() * 0.3f);
            left = (int) (mProgressBgBitmap.getWidth() * 0.2f);
            top = (int) (height * 0.64f);
            RectF rectF = new RectF(left, top, left + currentProgressWidth, top + progressHeight);
            int round = progressHeight / 2;
            // 设置进度条渐变颜色
            Shader shader = new LinearGradient(0, 0, currentProgressWidth, 0, new int[]{mProgressStarColor, mProgressEndColor}, new float[]{0, 1.0f}, Shader.TileMode.CLAMP);
            mProgressPaint.setShader(shader);
            canvas.drawRoundRect(rectF, round, round, mProgressPaint);

            bombCenterX = currentProgressWidth + left;
            bombCenterY = top - progressHeight / 2;
        }

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

    private synchronized void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    public void setTotalProgress(int totalProgress) {
        this.mTotalProgress = totalProgress;
    }

    public void startAnimation(int from, int to) {// 1,2
        if(mProgressAnimator == null) {
            mProgressAnimator = ValueAnimator.ofFloat(from, to);
            mProgressAnimator.setDuration(600);
            mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float current = (float) animation.getAnimatedValue();
                    setCurrentProgress(current);
                }
            });

            // 我们的进度条涨满之后，就可以开始执行我们的扩散爆炸
            mProgressAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    executeBombAnimator();
                }
            });
        }
        mProgressAnimator.start();
    }

    /**
     * 执行扩散动画
     */
    private void executeBombAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBombProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(mCurrentProgress == mTotalProgress){
                    // 缩小放大 ，执行喷洒（Lottie ，帧动画）
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 做最后处理，再去看下内存泄漏
        // LottieAnimationView 小坑（性能），不要反复去设置 addListener 10次 ANR
    }
}
