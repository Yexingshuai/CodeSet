package uitest.star.com.uiset.demo.customview.qqslide;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.adapter.base.BaseRecyclerAdapter;
import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;
import uitest.star.com.uiset.ui.widget.DragLayout;
import uitest.star.com.uiset.utils.StatusBarUtil;

public class QQSlideActivity extends UIActivity {


    private RecyclerView mRecyclerView;
    private String[] names = {"Ashbur", "Baird", "Bart", "Clifford", "Darnell ", "Eden", "Edgar", "Franklin", "Gilbert ", "Haley", "James", "Kenneth ", "Lester", "Marvin", "Nathaniel"};
    private List<String> mList;
    private DragLayout mDragLayout;
    private ImageView mHeader;
    private RecyclerView mLeftRecyclerView;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_qqslide;
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setStatusBarColor2(this, false);
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mDragLayout = findViewById(R.id.dragLayout);
        mHeader = findViewById(R.id.iv_head);
        mLeftRecyclerView = findViewById(R.id.left_Recyclerview);

    }

    @Override
    protected void initData() {
        mList = Arrays.asList(names);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BaseRecyclerAdapter<String>(this, R.layout.item_qqitem, mList) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {
                holder.setText(R.id.tv, mList.get(position));
            }
        });
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mLeftRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mLeftRecyclerView.setAdapter(new BaseRecyclerAdapter<String>(this, R.layout.item_qq_left_item, mList) {
            @Override
            protected void onBindHolder(BaseViewHolder holder, String s, int position) {
                holder.setText(R.id.tv, mList.get(position));
            }
        });

        mDragLayout.setOnDragStatusChangeListener(new DragLayout.OnDragStatusChangeListener() {
            @Override
            public void closed() {

                ObjectAnimator animator = ObjectAnimator.ofFloat(mHeader, "translationX", 10);
                animator.setDuration(500);
                animator.setInterpolator(new CycleInterpolator(3));
                animator.start();
            }

            @Override
            public void opened() {

            }

            @Override
            public void draging(float percent) {

            }
        });
    }

}
