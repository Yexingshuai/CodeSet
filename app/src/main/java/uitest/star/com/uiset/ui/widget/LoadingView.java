package uitest.star.com.uiset.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by yexing on 2019/1/10.
 */

public class LoadingView extends RelativeLayout {

    private CircleView mLeftView, mMiddleView, mRightView;
    private int mTranslationDistance = 20;
    private long animation_time = 350;
    private boolean mIsStopAnimator = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dp2px(30);
        //添加三个圆
        mLeftView = getCircleView(context);
        mLeftView.exChangeColor(Color.BLUE);
        mMiddleView = getCircleView(context);
        mMiddleView.exChangeColor(Color.RED);
        mRightView = getCircleView(context);
        mRightView.exChangeColor(Color.GREEN);
        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);

        post(new Runnable() {
            @Override
            public void run() {
                //布局实例化后 再去开启动画
                exPand();
            }
        });

    }

    /**
     * 开启动画
     */
    private void exPand() {
        if (mIsStopAnimator) return;
        AnimatorSet animatorSet = new AnimatorSet();
        //左边动画
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -mTranslationDistance);
        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0, mTranslationDistance);

        //弹性效果 (荡秋千)
        animatorSet.setInterpolator(new DecelerateInterpolator()); //2f 代表明显效果

        animatorSet.setDuration(animation_time);
        animatorSet.playTogether(leftAnimator, rightAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                innerAnimator();
            }
        });

        animatorSet.start();

    }

    private void innerAnimator() {
        if (mIsStopAnimator) return;
        AnimatorSet animatorSet = new AnimatorSet();
        //左边动画
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", -mTranslationDistance, 0);
        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", mTranslationDistance, 0);
        animatorSet.setDuration(animation_time);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(leftAnimator, rightAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //切换颜色顺序， 左边给中间，中间给右边，右边给左边
                int lefeColor = mLeftView.getColor();
                int middleColor = mMiddleView.getColor();
                int rightColor = mRightView.getColor();
                mMiddleView.exChangeColor(lefeColor);
                mLeftView.exChangeColor(rightColor);
                mRightView.exChangeColor(middleColor);
                exPand();
            }
        });

        animatorSet.start();
    }

    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dp2px(10), dp2px(10));
        layoutParams.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(layoutParams);
        return circleView;
    }

    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);
        mLeftView.clearAnimation();
        mRightView.clearAnimation();
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
            removeAllViews();
        }
        mIsStopAnimator = true;

    }
}
