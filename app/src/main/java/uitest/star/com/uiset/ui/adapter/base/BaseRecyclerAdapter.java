package uitest.star.com.uiset.ui.adapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uitest.star.com.uiset.ui.adapter.OnItemClickListener;

/**
 * Created by yexing on 2018/12/12.
 */


/***
 *  单类型条目适配器
 * @param <T>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    private Context mContext;
    private int resId;
    private List<T> mList;
    private OnItemClickListener mOnItemClickListener;
    /*
    条目内控件点击事件
     */
    private int[] mClickIds;
    private View.OnClickListener mOnClickListener;

    public BaseRecyclerAdapter(Context context, @NonNull int resId, List<T> list) {
        mContext = context;
        this.resId = resId;
        mList = list;
    }


    public BaseRecyclerAdapter(Context context, @NonNull int resId, List<T> list, OnItemClickListener onItemClickListener) {
        mContext = context;
        this.resId = resId;
        mList = list;
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRecyclerAdapter(Context context, @NonNull int resId, List<T> list, OnItemClickListener onItemClickListener, int[] clickIds, View.OnClickListener onClickListener) {
        mContext = context;
        this.resId = resId;
        mList = list;
        mOnItemClickListener = onItemClickListener;
        mClickIds = clickIds;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(resId, parent, false);
//
//        for (int i = 0; i < getItemCount(); i++) {
//            if (viewType == mList.get(i).getItemType()) {
//                return mList.get(i).onCreateViewHolder(parent, viewType);
//            }
//
//        }
//        throw new RuntimeException("wrong viewTyep");

        return new BaseViewHolder(this, mContext, view, mOnItemClickListener, mClickIds, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        T t = mList.get(position);
        onBindHolder(holder, t, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 填充UI
     *
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void onBindHolder(BaseViewHolder holder, T t, int position);


    /**
     * 当条目被点击时
     *
     * @param view
     * @param position
     */
    public void onItemViewClick(BaseViewHolder holder,View view, int position) {

    }

    ;
}
