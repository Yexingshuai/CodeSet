package uitest.star.com.uiset.demo.customview.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;
import uitest.star.com.uiset.utils.StatusBarUtil;

public class CarHomeActivity extends UIActivity {


    private RecyclerView mRecyclerView;
    private List<String> list;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_car_home;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = getResources().getStringArray(R.array.personName);
        list = Arrays.asList(array);
        mRecyclerView.setAdapter(new BaseRecyclerAdapter<String>(this, R.layout.item_qqitem, list) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {
                holder.setText(R.id.tv, list.get(position));
            }
        });
    }
}
