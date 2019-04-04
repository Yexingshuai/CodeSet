package uitest.star.com.uiset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.base.BaseFragment;
import uitest.star.com.uiset.entity.Tab;
import uitest.star.com.uiset.ui.adapter.OnItemClickListener;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;

/**
 * Created by yexing on 2019/1/4.
 */

public class ItemFragment extends BaseFragment {

    public static final String RECYCLER_LIST = "recycler_list";
    private RecyclerView mRecyclerView;
    private ArrayList<Tab> mList;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            Tab tab = mList.get(position);
            if (tab.clz != null) {
                startActivity(new Intent(getActivity(), tab.clz));
            }
        }
    };


    public static ItemFragment newInstance(List<Tab> tabList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECYCLER_LIST, (Serializable) tabList);
        ItemFragment itemFragment = new ItemFragment();
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initialize() {
        mList = (ArrayList<Tab>) getArguments().getSerializable(RECYCLER_LIST);
    }

    @Override
    public void initView() {
        mRecyclerView = getView(R.id.recyclerview);
    }

    @Override
    public void initData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        BaseRecyclerAdapter<Tab> adapter = new BaseRecyclerAdapter<Tab>(getActivity(), R.layout.item_tab, mList, null, new int[]{R.id.bt}, onClickListener) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, Tab tab, int position) {
                holder.setText(R.id.bt, tab.name);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }


}
