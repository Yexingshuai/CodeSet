package uitest.star.com.uiset.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2018/12/28.
 */

public class SplashView extends View {
    public static final int ANIMTIME = 5000;

    //当前大圆旋转角度
    private float mCurrentRotationAngle = 0f;
    private int[] mCircleColors;
    //以屏幕中心为原点的
    private float mRotationRadius;
    //每个小圆的半径是 大圆半径的8分之一
    private float mCircleRadius;

    private boolean isInitParms;

    private Paint mPaint;

    //中心点
    private int mCenterX, mCenterY;
    private ValueAnimator valueAnimator;

    //代表当前状态 所画动画
    private LoadingState loadingState;

    //当前大圆半径
    private float mCurrentRotationRadius = mRotationRadius;

    //当前空心圆半径
    private float mHoleRadius = 0f;

    //屏幕对角线的一半
    private float mDiagonalDist;

    // 整体的颜色背景
    private int mSplashColor = Color.WHITE;

    // 绘制背景的画笔
    private Paint mPaintBackground = new Paint();

    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取小圆的颜色列表
        mCircleColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitParms) {
            initParms();
            isInitParms = !isInitParms;
        }
        if (loadingState == null) {
            loadingState = new RotationState();
        }
        loadingState.draw(canvas);
    }

    /**
     * 初始化参数
     */
    private void initParms() {
        mRotationRadius = getMeasuredHeight() / 5;
        //每个小圆的半径
        mCircleRadius = mRotationRadius / 8;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        //绘制背景的画笔
        mPaintBackground.setDither(true);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashColor);

        mDiagonalDist = (float) Math.sqrt(mCenterX * mCenterX + mCenterY * mCenterY);
    }


    public void disappear() {
        //开始聚合动画
        //关闭旋转动画
        if (loadingState instanceof RotationState) {
            RotationState rotationState = (RotationState) loadingState;
            rotationState.cancel();
            loadingState = new MergeState();
        }


    }

    public abstract class LoadingState {

        public abstract void draw(Canvas canvas);
    }

    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {

        private ValueAnimator valueAnimator;

        public RotationState() {
            //用一个变量不断的去改变， 用属性动画， 旋转0-360
            if (valueAnimator == null) {

                valueAnimator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI);
                valueAnimator.setDuration(ANIMTIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mCurrentRotationAngle = (float) valueAnimator.getAnimatedValue();
                        //重新绘制
                        invalidate();
                    }
                });
                valueAnimator.setInterpolator(new LinearInterpolator());
                //不断执行
                valueAnimator.setRepeatCount(-1);
                valueAnimator.start();
            }
        }

        @Override
        public void draw(Canvas canvas) {
            //画一个白色背景
            canvas.drawColor(Color.WHITE);
            //画六个圆    每份的角度   (1.04多)
            double percentAngle = Math.PI * 2 / mCircleColors.length;
            for (int i = 0; i < mCircleColors.length; i++) {
                mPaint.setColor(mCircleColors[i]);

                //当前的角度= 初始角度+  旋转角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        /**
         * 取消动画
         */
        public void cancel() {
            valueAnimator.cancel();
            valueAnimator=null;
        }
    }

    /**
     * 聚合动画
     */
    public class MergeState extends LoadingState {

        private ValueAnimator valueAnimator;

        public MergeState() {
            valueAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0);
            valueAnimator.setInterpolator(new AnticipateInterpolator(3f));
            valueAnimator.setDuration(ANIMTIME / 2);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mCurrentRotationRadius = (float) valueAnimator.getAnimatedValue();  //半径 到0
                    //重新绘制
                    invalidate();
                }
            });

            //等待聚合完毕，开始展开
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    loadingState = new ExpendState();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            valueAnimator.start();

        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSplashColor);
            //画六个圆    每份的角度   (1.04多)
            double percentAngle = Math.PI * 2 / mCircleColors.length;
            for (int i = 0; i < mCircleColors.length; i++) {
                mPaint.setColor(mCircleColors[i]);

                //当前的角度= 初始角度+  旋转角度
                double currentAngle = percentAngle * i + mCurrentRotationAngle;
                int cx = (int) (mCenterX + mCurrentRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mCurrentRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }

        }
    }

    /**
     * 展开动画
     */
    public class ExpendState extends LoadingState {
        private ValueAnimator valueAnimator;

        public ExpendState() {
            valueAnimator = ObjectAnimator.ofFloat(0, mDiagonalDist);
            valueAnimator.setDuration(ANIMTIME/2);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mHoleRadius = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();

        }

        @Override
        public void draw(Canvas canvas) {
//            //画笔的宽度
//            float strokeWidth = mDiagonalDist - mHoleRadius;
//            mPaint.setStrokeWidth(strokeWidth);
//            mPaint.setStyle(Paint.Style.STROKE);
//
//            float radius = strokeWidth / 2 + mHoleRadius;
//            //绘制一个圆形
//            canvas.drawCircle(mCenterX, mCenterY, mHoleRadius, mPaint);

            if (mHoleRadius > 0) {
                float strokeWidth = mDiagonalDist - mHoleRadius;
                mPaintBackground.setStrokeWidth(strokeWidth);
                float radius = mHoleRadius + strokeWidth / 2;
                canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);
            } else {
                canvas.drawColor(mSplashColor);
            }
        }
    }


}
