package uitest.star.com.uiset.demo.view.dropdown;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;
import uitest.star.com.uiset.ui.widget.DropDownMenu;
import uitest.star.com.uiset.utils.ToastUtil;

public class DropdownViewActivity extends UIActivity {

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String headers[] = {"城市", "自定义布局"};
    private String sexs[] = {"不限", "男", "女"};
    private ListDropDownAdapter sexAdapter;
    private ArrayList<View> popupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;


    @Override
    protected int inflateLayout() {
        return R.layout.activity_dropdown_view;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mDropDownMenu = findViewById(R.id.dropDownMenu);
    }

    @Override
    protected void initData() {
        //正文
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(new BaseRecyclerAdapter<String>(this, R.layout.item_list_drop_down, Arrays.asList(citys)) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {
                holder.setText(R.id.text, s);

            }

            @Override
            public void onItemViewClick(BaseViewHolder holder, View view, int position) {
                mDropDownMenu.setTabText(citys[position]);
                ToastUtil.showApp(citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        View customLayout = LayoutInflater.from(this).inflate(R.layout.dropdown_custom, null);

        popupViews.add(recyclerView);
        popupViews.add(customLayout);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }
}
