package uitest.star.com.uiset.demo.customview.red;

import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.RedPackageView2;

public class RedPackageActivity extends UIActivity {


    private RedPackageView2 red_pack_view;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_red_package;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        red_pack_view = findViewById(R.id.red_pack_view);
        red_pack_view.setTotalProgress(3);
        red_pack_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                red_pack_view.startAnimation(1,3);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
