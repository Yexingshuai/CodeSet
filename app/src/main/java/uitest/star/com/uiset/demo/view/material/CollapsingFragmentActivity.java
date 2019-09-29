package uitest.star.com.uiset.demo.view.material;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import uitest.star.com.uiset.R;


public class CollapsingFragmentActivity extends AppCompatActivity {

    private String[] mTitle = {"自定义View", "控件", "架构"};
    private Toolbar toolbar;
    private SwipeRefreshLayout swipe;
    private boolean mScaling;
    private ImageView ivBg;
    private LinearLayout llRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsingfg);
        initView();
        initData();
    }

    private void initView() {
        swipe = findViewById(R.id.swipe);
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout mTabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mTitle, null));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewPager);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        AppBarLayout appbar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                Log.e("appbarlayout---", offset + "---");
                float alphaF = ((float) Math.abs(offset)) / totalScrollRange;
                toolbar.setAlpha(alphaF);
                if (swipe != null) {
                    if (offset >= 0) {
                        swipe.setEnabled(true);
                    } else {
                        swipe.setEnabled(false);
                    }

                }
            }
        });
    }

    private void initData() {
    }
}
