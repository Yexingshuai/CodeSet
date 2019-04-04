package uitest.star.com.uiset.demo.customview.Loading;

import android.os.Handler;
import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.LoadingView;
import uitest.star.com.uiset.utils.StatusBarUtil;

/**
 * Created by yexing on 2019/1/10.
 */

public class LoadingViewActivity extends UIActivity {

    private LoadingView loadingView;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_loadingview;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void initView() {
        loadingView = findViewById(R.id.loadingView); //注意这个动画
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
            }
        }, 1000 * 10);
    }
}
