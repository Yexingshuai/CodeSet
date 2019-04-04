package uitest.star.com.uiset.demo.customview.menu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2019/1/23.
 */

public class ListScreenMenuAdapter extends BaseMenuAdapter {

    private String[] mItems = {"类型", "品牌", "价格", "更多"};
    private Context mContext;

    public ListScreenMenuAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen, parent, false);
        textView.setText(mItems[position]);
        return textView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView menuView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.ui_list_data_screen, parent, false);
        menuView.setText(mItems[position]);
        return menuView;
    }

    @Override
    public void menuOpen(View tabView) {
        TextView tabTv = (TextView) tabView;
//        tabTv.setTextColor(Color.GRAY);
    }

    @Override
    public void menuClose(View tabView) {
        TextView tabTv = (TextView) tabView;
//        tabTv.setTextColor(Color.BLACK);
    }
}
