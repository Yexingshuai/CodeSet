package uitest.star.com.uiset.demo.view.material;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * CoordinatorLayout  一个加强版的FrameLayout，可以监听所有子控件的各种事件
 */

public class MaterialDesignActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialdesign);
        initialize();
        initView();
        initData();
    }

    protected void initialize() {

    }

    protected void initView() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "哈哈哈哈哈哈", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showApp("Undo.................");
                    }
                })
                        .show();
            }
        });

        //SnakeBar 传入的这个 view 是当前界面布局的任意一个view就行，SnakeBar会使用这个View来自动查找最外层的布局，用于展示SnackBar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//将toolbar的实例传入，就做到了既使用了ToolBar,又让他的外观与功能都和ActionBar一致了。

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //是否显示左上角图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_add_a_photo_black_24dp);
        }

        Button btCard = findViewById(R.id.bt_card);
        btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialDesignActivity.this, MateriaCardActivity.class));
            }
        });

        Button bt_fold = findViewById(R.id.bt_fold);
        bt_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialDesignActivity.this, CollapsingActivity.class));
            }
        });

    }

    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottonnavagationview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                ToastUtil.showApp("点击了第一个按钮");
                break;
            case R.id.item2:
                ToastUtil.showApp("点击了第二个按钮");
                break;
            case R.id.item3:
                ToastUtil.showApp("点击了第三个按钮");
                break;
            case android.R.id.home:
                ToastUtil.showApp("你点击了我！");
                break;
        }
        return true;
    }
}
