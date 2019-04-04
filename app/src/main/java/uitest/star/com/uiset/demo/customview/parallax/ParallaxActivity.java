package uitest.star.com.uiset.demo.customview.parallax;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.ParallaxViewPager;
import uitest.star.com.uiset.utils.StatusBarUtil;

/**
 * Created by yexing on 2019/1/7.
 */

public class ParallaxActivity extends UIActivity {

    private ParallaxViewPager parallaxViewPager;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_parallax;
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setStatusBarColor2(this,false);
    }

    @Override
    protected void initView() {
        parallaxViewPager = findViewById(R.id.parallax_vp);
    }

    @Override
    protected void initData() {
        parallaxViewPager.setLayout(getSupportFragmentManager(), new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second, R.layout.fragment_page_third});
    }
}
