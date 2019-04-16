package uitest.star.com.uiset.demo.view.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;


/**
 * AppBarLayout  实际上是一个垂直方向的LinearLayout，它在内部做了很多滚动事件的封装，
 * 在RecyvlerView中使用了一个app：layout_behavior属性 指定了一个布局行为：appbar_scrolling_view_behavior 可解决toolbar遮挡Recyclerview问题
 * 当RecyclerView滑动的时候 就已经将滚动事件都通知给AppBarLayout了。
 * <p>
 * 在ToolBar当中 添加了一个app:layout_scrollFlags 属性，将这个属性指定指定成了scroll|enterAlways|snap
 * <p>
 * scroll: 当Recyclerview滑动时，toolbar会跟着一起向上滚动并实现隐藏
 * enterAlways：表示当RecyclerView向下滑动时，Toolbar会跟着一起向下滚动 和隐藏
 * snap： 表示 当Toolbar还没有完全隐藏或显示的时候，会根据当前滑动距离，自动选择是隐藏还是展示
 */
public class MateriaCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_card);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(this, R.layout.item_material_card, list) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {

            }
        };
        recyclerView.setAdapter(adapter);
    }


    private void initData() {
    }


}
