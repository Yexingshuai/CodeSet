package uitest.star.com.uiset.demo.view.toolbar;

import android.view.ViewGroup;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;


/**
 * 已烂尾！！！！！
 */

public class ToolBarActivity extends UIActivity {


    @Override
    protected int inflateLayout() {
        return R.layout.activity_tool_bar;
    }

    @Override
    protected void initialize() {

        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();  //获取Activity的根布局
        ViewGroup parent = (ViewGroup) decorView.getChildAt(0);//Activity最外层的布局

        DefaultNavigationBar.Builder builder = new DefaultNavigationBar.Builder(this, parent);
        builder.setTitle("李日天");
//        builder.create();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
