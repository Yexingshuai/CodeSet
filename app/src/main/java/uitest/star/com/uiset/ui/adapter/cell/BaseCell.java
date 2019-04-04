package uitest.star.com.uiset.ui.adapter.cell;

/**
 * Created by yexing on 2018/12/14.
 */

public abstract class BaseCell<T> implements Cell {


    public T mData;

    public BaseCell(T t) {
        mData = t;
    }

    @Override
    public void releaseResource() {
        // do nothing
        //如果有需要回收的资源，子类自己实现

    }
}
