package uitest.star.com.uiset.demo.framework.alive;


import android.content.Intent;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;

public class KeepAliveServiceActivity extends UIActivity {


    @Override
    protected int inflateLayout() {
        return R.layout.activity_keep_alive_service;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        startService(new Intent(this, MessageService.class));
        startService(new Intent(this, GuardService.class));
    }
}
