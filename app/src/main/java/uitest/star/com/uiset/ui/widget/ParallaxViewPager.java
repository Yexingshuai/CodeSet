package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.demo.customview.parallax.ParallaxFragment;
import uitest.star.com.uiset.demo.customview.parallax.ParallaxTag;

/**
 * Created by yexing on 2019/1/7.
 */

public class ParallaxViewPager extends ViewPager {

    private List<ParallaxFragment> mFragments = new ArrayList<>();

    public ParallaxViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 设置布局数组
     *
     * @param layoutId
     */
    public void setLayout(FragmentManager fm, int[] layoutId) {

        for (int id : layoutId) {
            ParallaxFragment parallaxFragment = ParallaxFragment.newInstance(id);
            mFragments.add(parallaxFragment);
        }
        //设置adapter
        setAdapter(new ParallaxAdapter(fm));
        //监听位移

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //获取左右fragment
                // 滚动  position 当前位置    positionOffset 0-1     positionOffsetPixels 0-屏幕的宽度px
                Log.e("TAG", "position->" + position + " positionOffset->" + positionOffset + " positionOffsetPixels->" + positionOffsetPixels);

                // 获取左out 右 in
                ParallaxFragment outFragment = mFragments.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    // 为什么这样写 ？
                    parallaxView.setTranslationX((-positionOffsetPixels) * tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels) * tag.translationYOut);
                }

                try {
                    ParallaxFragment inFragment = mFragments.get(position + 1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn);
                    }
                } catch (Exception e) {
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class ParallaxAdapter extends FragmentPagerAdapter {
        public ParallaxAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
