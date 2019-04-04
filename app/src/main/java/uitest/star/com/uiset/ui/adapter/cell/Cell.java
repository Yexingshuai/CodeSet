package uitest.star.com.uiset.ui.adapter.cell;

import android.view.ViewGroup;

import uitest.star.com.uiset.ui.adapter.base.BaseViewHolder;

/**
 * Created by yexing on 2018/12/14.
 */

public interface Cell {

    /**
     * 回收资源
     */
    public void releaseResource();


    /**
     * 获取条目类型
     *
     * @return
     */
    public int getItemType();


    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);


    /**
     * 数据绑定
     *
     * @param holder
     * @param postion
     */
    public void onBindViewHolder(BaseViewHolder holder, int postion);
}
