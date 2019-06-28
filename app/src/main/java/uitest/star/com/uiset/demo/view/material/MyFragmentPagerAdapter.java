package uitest.star.com.uiset.demo.view.material;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;




/**
 * Created by yexing on 2019/1/7.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    private String[] mTitle;
    private List[] mList;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] title, List[] list) {
        super(fm);
        mTitle = title;
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
