package uitest.star.com.uiset.demo.view.okdialog.dialog;

/**
 * Created by yexing on 2019/1/18.
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Dialog View 的辅助处理类
 */
class DialogViewHelper {

    private View mContentView = null;

    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, int viewLayoutId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    /**
     * 设置布局View
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    /**
     * 设置点击
     *
     * @param id
     * @param onClickListener
     */
    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
//        View view = mContentView.findViewById(id);  //会比较麻烦

        View view = getView(id);
        //优化
        getView(id);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }


    /**
     * 设置文本
     *
     * @param id
     * @param text
     */
    public void setText(int id, CharSequence text) {
        TextView tv = getView(id); //减少findviewbid 次数
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 获取view
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
//        View view = mViews.get(id).get();  如果为空会报错
        WeakReference<View> viewWeakReference = mViews.get(id);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(id);
            if (view != null) {         //也有可能找不到
                mViews.put(id, new WeakReference<>(view));
            }
        }
        return (T) view;
    }


    /**
     * 获取contentView
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
