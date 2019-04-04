package uitest.star.com.uiset.demo.view.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.utils.StatusBarUtil;

/**
 * 常用app UI 页面
 * 非原创
 */

public class AppUIActivity extends UIActivity implements View.OnClickListener {

    private TextView mTabNews, mTabHotGoogs, mTabCommunity;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_app_ui;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        Button loginButton = findViewById(R.id.bt_login);
        loginButton.setOnClickListener(this);
        mTabNews = findViewById(R.id.tv_tab_news);
        mTabHotGoogs = findViewById(R.id.tv_tab_hotgoods);
        mTabCommunity = findViewById(R.id.tv_tab_community);
        mTabNews.setOnClickListener(this);
        mTabHotGoogs.setOnClickListener(this);
        mTabCommunity.setOnClickListener(this);
        mTabNews.setSelected(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.tv_tab_news:
                mTabNews.setSelected(true);
                mTabCommunity.setSelected(false);
                mTabHotGoogs.setSelected(false);
                break;
            case R.id.tv_tab_hotgoods:
                mTabHotGoogs.setSelected(true);
                mTabNews.setSelected(false);
                mTabCommunity.setSelected(false);
                break;
            case R.id.tv_tab_community:
                mTabCommunity.setSelected(true);
                mTabNews.setSelected(false);
                mTabHotGoogs.setSelected(false);
                break;
        }
    }
}
