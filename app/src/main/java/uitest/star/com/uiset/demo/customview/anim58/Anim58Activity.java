package uitest.star.com.uiset.demo.customview.anim58;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.entity.Person;
import uitest.star.com.uiset.ui.widget.LoadingView;
import uitest.star.com.uiset.ui.widget.LoadingView58;
import uitest.star.com.uiset.ui.widget.ShapeView;

public class Anim58Activity extends UIActivity {


    private LoadingView58 mLoadingView;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_anim58;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mLoadingView = findViewById(R.id.loadingView);
    }

    @Override
    protected void initData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingView.setVisibility(View.INVISIBLE);
            }
        }, 10000);
    }


}
