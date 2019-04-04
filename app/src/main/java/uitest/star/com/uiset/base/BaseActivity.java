package uitest.star.com.uiset.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.helper.ActivityStackManager;
import uitest.star.com.uiset.utils.StatusBarUtil;

/**
 * Created by yexing on 2018/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG_LOG = this.getClass().getSimpleName();

    private Fragment mFragment;

    private View.OnClickListener mOnNavClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateLayout()); //

        ActivityStackManager.getInstance().onActivityCreated(this);

        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            //不显示默认的Title
            actionBar.setDisplayShowTitleEnabled(true);
            //不显示默认的Home（an activity icon or logo.）
            actionBar.setDisplayShowHomeEnabled(false);
            //是否显示左上角的返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationIcon(R.mipmap.icon_back);
            toolbar.setTitle(getTitle());
            toolbar.setNavigationOnClickListener(mOnNavClickListener);
        }

        StatusBarUtil.setStatusBarColor2(this, true);
        initialize();
        initView();
        initData();
    }

    @Override
    public void finish() {
        hideSoftKeyboard();
        super.finish();
    }


    /**
     * 设置标题
     *
     * @param title
     */
    protected void setActionTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }

    public <T> T getView(int id) {
        return (T) findViewById(id);
    }


    /**
     * 填充布局
     *
     * @return
     */
    protected abstract int inflateLayout();

    protected abstract void initialize();

    /**
     * 查找控件
     */
    protected abstract void initView();

    /**
     * 初始化
     */
    protected abstract void initData();




    /**
     * startActivity 方法优化
     */

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivityFinish(new Intent(this, cls));
    }

    public void startActivityFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 获取一个 Context 对象
     */
    public Context getContext() {
        return getBaseContext();
    }


    /**
     * 获取当前 Activity 对象
     */
    public <A extends BaseActivity> A getActivity() {
        return (A) this;
    }


    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }


}

