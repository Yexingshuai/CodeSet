package uitest.star.com.uiset.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2019/1/11.
 */

public class LoadingView58 extends LinearLayout {

    private ShapeView mShapeView;
    private View mShadowView;
    private int mTranslationYdistance = 80;
    private ObjectAnimator rotationAnimator;
    //是否结束动画
    private boolean mIsStopAnimator = false;

    public LoadingView58(Context context) {
        this(context, null);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView58(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initLayout() {
        //添加布局
//        View view = inflate(getContext(), R.layout.ui_loadingview, this);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ui_loadingview, this, false);
        addView(view);
        mShapeView = findViewById(R.id.shapeView);
        mShadowView = findViewById(R.id.view);
        post(new Runnable() {
            @Override
            public void run() {
                //在onResume 之后开始 执行
                startFallAnimator();
            }
        });

    }

    /**
     * 下落动画  现实生活中，自由落体 越往后面 速度越快。 向上抛物，越往后面，速度越慢。 添加差值器
     */
    private void startFallAnimator() {
        if (mIsStopAnimator) {
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, dp2px(mTranslationYdistance));
        translationAnimator.setDuration(500);
        //配合阴影动画 缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f);
        scaleAnimator.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scaleAnimator);

        //想执行前面的动画，在执行后面的动画
//        animatorSet.playSequentially(translationAnimator, scaleAnimator);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //执行上抛动画
                startUpAnimtor();

                //开始转圈
            }
        });
        animatorSet.start();

        //下落完之后 要向上抛,  监听动画完毕

    }

    /**
     * 上抛动画
     */
    private void startUpAnimtor() {
        if (mIsStopAnimator) {
            return;
        }

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView, "translationY", dp2px(mTranslationYdistance), 0);
        translationAnimator.setDuration(500);
        //配合阴影动画 缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f);
        scaleAnimator.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationAnimator, scaleAnimator);

        animatorSet.setDuration(500);
        //上抛之后 下落
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //执行下落动画
                startFallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //改变形状
                mShapeView.exChange();
                //开始旋转
                startRotationAnimator();
            }
        });
        animatorSet.start();

    }

    /**
     * 上抛的时候旋转
     */
    private void startRotationAnimator() {
        switch (mShapeView.getCurrentShape()) {
            case Circle:
            case Square: //180
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, 180);
                break;
            case Triangle://
                rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, 120);
                break;

        }
        rotationAnimator.setDuration(500);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);  //如果设置为gone, 会执行很多逻辑
        //清理动画
        mShapeView.clearAnimation();
        mShadowView.clearAnimation();
        //把LoadingView从父布局移除
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this); //从父布局移除
            removeAllViews();//移除自己的所有的view
        }

        mIsStopAnimator = true;


    }
}
