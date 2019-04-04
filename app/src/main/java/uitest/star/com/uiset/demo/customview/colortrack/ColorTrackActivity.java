package uitest.star.com.uiset.demo.customview.colortrack;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.ColorTrackTextView;

public class ColorTrackActivity extends UIActivity {


    private ColorTrackTextView mColorTrackTextView;
    private String[] title = {"推荐", "喜欢", "生活", "美食", "旅行"};
    private LinearLayout ll_tab;
    private ViewPager mViewPager;

    private List<ColorTrackTextView> mIndicators = new ArrayList<>();

    @Override
    protected int inflateLayout() {
        return R.layout.activity_color_track;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mColorTrackTextView = findViewById(R.id.tv_colortrack);
        ll_tab = findViewById(R.id.ll_tab);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        initIndicator();
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(title[position]);
            }

            @Override
            public int getCount() {
                return title.length;
            }
        });
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initIndicator() {
        for (int i = 0; i < title.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setText(title[i]);
            colorTrackTextView.setLayoutParams(params);
            ll_tab.addView(colorTrackTextView);
            mIndicators.add(colorTrackTextView);
        }
    }

    public void leftToRight(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TORIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mColorTrackTextView.setProgress(animatedValue);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TOLEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mColorTrackTextView.setProgress(animatedValue);
            }
        });
        valueAnimator.start();
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 参1：当前viewpager的position,  参2：滑动比列， 参3： 滑动像素
         *
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            ColorTrackTextView left = mIndicators.get(position);
            left.setDirection(ColorTrackTextView.Direction.RIGHT_TOLEFT);
            left.setProgress(1 - positionOffset);

            try {
                ColorTrackTextView right = mIndicators.get(position + 1);
                right.setDirection(ColorTrackTextView.Direction.LEFT_TORIGHT);
                right.setProgress(positionOffset);

            } catch (Exception e) {
            }


        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
