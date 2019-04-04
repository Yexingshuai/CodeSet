package uitest.star.com.uiset.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import uitest.star.com.uiset.ui.adapter.OnItemClickListener;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2018/12/12.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private View.OnClickListener mChildrenClickListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setTag(getCurrentClickPosition());
            if (mChildrenClickListener != null) {
                mChildrenClickListener.onClick(view);
            }
        }
    };


    public BaseViewHolder(View view) {
        super(view);
    }

    public BaseViewHolder(final BaseRecyclerAdapter adapter, Context context, View view, final OnItemClickListener onItemClickListener, int[] mClickIds, View.OnClickListener onClickListener) {
        this(view);
        this.mContext = context;
        mChildrenClickListener = onClickListener;

        mOnItemClickListener = onItemClickListener;
        itemView.setClickable(true);
        /**
         * Item Click
         */
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, getCurrentClickPosition());
                }
                adapter.onItemViewClick(BaseViewHolder.this, view, getCurrentClickPosition());

            }
        });

        /**
         * Item's view click
         */

        if (mClickIds != null && mClickIds.length != 0) {
            for (int i = 0; i < mClickIds.length; i++) {
                getViewById(mClickIds[i]).setOnClickListener(mOnClickListener);
            }
        }
    }


    /**
     * 获取itemView
     *
     * @return
     */
    public View getItemView() {
        return this.itemView;
    }


    public void setText(int viewId, String text) {
        TextView textView = getViewById(viewId);
        textView.setText(text);
    }


    /**
     * 查找控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getViewById(int viewId) {
        T view = itemView.findViewById(viewId);
        return view;
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (V) view;
    }


    public int getCurrentClickPosition() {
        return getAdapterPosition();
    }
}
