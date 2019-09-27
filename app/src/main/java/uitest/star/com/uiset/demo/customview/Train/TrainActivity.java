package uitest.star.com.uiset.demo.customview.Train;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import uitest.star.com.ioclibrary.ioc.ViewById;
import uitest.star.com.ioclibrary.ioc.ViewUtils;
import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.utils.ToastUtil;

public class TrainActivity extends UIActivity {

    @ViewById(R.id.tv_left)
    private AppCompatTextView mLeftText;

    @ViewById(R.id.tv_right)
    private AppCompatTextView mRightText;
    private int startX;
    private int endX;


    @Override
    protected int inflateLayout() {
        return R.layout.activity_train;
    }

    @Override
    protected void initialize() {
        ViewUtils.inject(this);
    }

    @Override
    protected void initView() {
        mLeftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showApp("北京");
            }
        });

        mRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showApp("上海");
            }
        });
    }

    @Override
    protected void initData() {

    }


    /**
     * 替换动画
     *
     * @param view
     */
    public void replace(View view) {
        if (startX == 0 || endX == 0) {
            getLocation();
        }

        int leftMoveX = endX - startX;
        int rightMoveX = endX - startX;

        ValueAnimator startCityAnimation = ValueAnimator.ofInt(0, leftMoveX).setDuration(200);
        startCityAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mLeftText.layout(startX + animatedValue, mLeftText.getTop(), startX + animatedValue + mLeftText.getWidth(), mLeftText.getBottom());
            }
        });
        startCityAnimation.start();


        ValueAnimator endCityAnimation = ValueAnimator.ofInt(0, rightMoveX).setDuration(200);
        endCityAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mRightText.layout(endX - animatedValue, mRightText.getTop(), endX - animatedValue + mRightText.getWidth(), mRightText.getBottom());

            }
        });
        endCityAnimation.start();

        endCityAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //用于下次交换
                AppCompatTextView tempTextView = mLeftText;
                mLeftText = mRightText;
                mRightText = tempTextView;
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    private void getLocation() {
        int[] startXlocation = new int[2];
        mLeftText.getLocationOnScreen(startXlocation);

        int[] endXlocation = new int[2];
        mRightText.getLocationOnScreen(endXlocation);

        startX = startXlocation[0];
        endX = endXlocation[0];

    }
}
