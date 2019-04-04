package uitest.star.com.uiset.demo.customview.side;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;
import uitest.star.com.uiset.ui.widget.SwipeLayout;
import uitest.star.com.uiset.utils.ToastUtil;

public class SidePullDelActivity extends UIActivity {


    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<String> adapter;
    private List list;


    @Override
    protected int inflateLayout() {
        return R.layout.activity_side_pull_del;
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
        String[] stringArray = getResources().getStringArray(R.array.personName);
        list = new ArrayList();
        for (String s : stringArray) {
            list.add(s);
        }
        adapter = new BaseRecyclerAdapter<String>(this, R.layout.item_swipe_del, list, null) {
            @Override
            protected void onBindHolder(final BaseViewHolder holder, String s, final int position) {
                holder.setText(R.id.tv, s);

                ((SwipeLayout) holder.itemView).setOnDeletaPanelClickListener(new SwipeLayout.onDeletePanelClickListener() {
                    @Override
                    public void clickCall() {
                        ToastUtil.showApp("呼叫");
                    }

                    @Override
                    public void clickDel() {
                        list.remove(holder.getAdapterPosition());
                        adapter.notifyItemRemoved(holder.getAdapterPosition());

                    }
                });

            }
        };
        mRecyclerView.setAdapter(adapter);
    }


}
