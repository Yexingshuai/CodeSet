package uitest.star.com.uiset.demo.customview.menu;

import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.ListDataScreenView;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/1/23.
 */

public class MenuActivity extends UIActivity {

    private ListDataScreenView mListDataScreenView;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mListDataScreenView = findViewById(R.id.list_data_scrren_view);
    }

    @Override
    protected void initData() {
        mListDataScreenView.setAdapter(new ListScreenMenuAdapter(this));
    }

    public void qqq(View view) {


        ToastUtil.showApp(String.format("s%金霄","pig"));

    }
}
