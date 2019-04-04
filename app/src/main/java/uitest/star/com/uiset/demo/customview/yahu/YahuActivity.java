package uitest.star.com.uiset.demo.customview.yahu;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.SplashView;
import uitest.star.com.uiset.utils.StatusBarUtil;

public class YahuActivity extends UIActivity {


    @Override
    protected int inflateLayout() {
        return R.layout.activity_yahu;
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setStatusBarColor2(this,false);
    }

    @Override
    protected void initView() {
        final SplashView splashView = findViewById(R.id.splash_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashView.disappear();
            }
        }, 5000);
    }

    @Override
    protected void initData() {

    }
}
