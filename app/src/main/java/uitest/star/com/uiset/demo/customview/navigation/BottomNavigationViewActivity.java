package uitest.star.com.uiset.demo.customview.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.utils.StatusBarUtil;

/**
 * Created by yexing on 2018/11/29.
 */

public class BottomNavigationViewActivity extends UIActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private MyFragment myFragment;

    @Override
    protected int inflateLayout() {
        return R.layout.activbity_bottomnavtion;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navagation_view);
        bottomNavigationView.setItemIconTintList(null);//不使用默认变色
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void initData() {
        myFragment = MyFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, myFragment).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                myFragment.setText("李");
                break;
            case R.id.item2:
                myFragment.setText("日");
                break;
            case R.id.item3:
                myFragment.setText("天");
                break;
            default:
                return false;
        }
        return true;
    }
}
