package uitest.star.com.uiset.demo.customview.menu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yexing on 2019/1/23.
 * 筛选菜单的adapter
 */

public abstract class BaseMenuAdapter {

    //获取总共条目
    public abstract int getCount();

    //获取当前TabView
    public abstract View getTabView(int position, ViewGroup parent);

    //获取当前菜单内容
    public abstract View getMenuView(int position, ViewGroup parent);

    public abstract void menuOpen(View tabView); //Tab展开

    public abstract void menuClose(View tabView);//Tab关闭
}
