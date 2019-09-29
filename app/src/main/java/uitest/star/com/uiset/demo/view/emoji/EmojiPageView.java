package uitest.star.com.uiset.demo.view.emoji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.demo.view.emoji.entity.EmojiBean;
import uitest.star.com.uiset.demo.view.emoji.preview.EmotionPreview;
import uitest.star.com.uiset.demo.view.emoji.utils.EmojiUtil;

public class EmojiPageView {

    private Context mContext;
    private int spanCount;
    private ViewPager mViewPager;
    private View mRootView;
    PopupWindow popupWindow = null;

    private List<EmojiBean> mEmojiList = new ArrayList<>();
    private GridView mGridView;
    private EditText editText;
    private SharedPreferences preferences;

    public EmojiPageView(Context context, int spanCount, int totalCount, List<EmojiBean> emojiList, ViewPager viewPager) {
        mContext = context;
        this.spanCount = spanCount;
        mViewPager = viewPager;
        prepareData(totalCount, emojiList);
        initView();
        initData();
//        mViewPager.setScroll(false);
    }

    /**
     * 整理数据
     *
     * @param totalCount
     * @param emojiList
     */
    private void prepareData(int totalCount, List<EmojiBean> emojiList) {
        if (emojiList.size() > totalCount || emojiList.size() == totalCount) {
            for (int i = 0; i < totalCount; i++) {
                mEmojiList.add(emojiList.get(0));
                emojiList.remove(0);
            }
            mEmojiList.add(new EmojiBean());//无效值
        } else {
            int size = emojiList.size();
            for (int i = 0; i < size; i++) {
                mEmojiList.add(emojiList.get(0));
                emojiList.remove(emojiList.get(0));
            }
            int listSize = mEmojiList.size();
            if (listSize != totalCount) {
                for (int i = 0; i < totalCount - listSize; i++) {
                    mEmojiList.add(new EmojiBean());//无效
                }
                mEmojiList.add(new EmojiBean());//无效
            }
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.emoji_page_view, null);
        mGridView = mRootView.findViewById(R.id.gridView);
        mGridView.setNumColumns(spanCount);
        mGridView.setAdapter(new GridAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmojiBean emojiBean = mEmojiList.get(position);

            }
        });
        EmotionPreview emotionPreview = new EmotionPreview(mContext);
        mGridView.setOnItemLongClickListener(emotionPreview);
        mGridView.setOnTouchListener(emotionPreview);
    }

    private void initData() {
    }


    public View getRootView() {
        return mRootView;
    }

    public class GridAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mEmojiList.size();
        }

        @Override
        public Object getItem(int position) {
            return mEmojiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_emoji_text, parent, false);
                viewHolder.iv = convertView.findViewById(R.id.tv);
                viewHolder.rootView = convertView.findViewById(R.id.rootView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (position != getCount() - 1 & mEmojiList.get(position).getId() == -1) {
                viewHolder.rootView.setVisibility(View.INVISIBLE); //如果实体对象无数据，需要占位
            }

            final EmojiBean emojiBean = mEmojiList.get(position);
            if (position == mEmojiList.size() - 1) { //说明是最后一个图标
                viewHolder.iv.setImageResource(R.mipmap.delete_emoji);
                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteEditText();
                    }
                });
            } else {
                int resource = EmojiUtil.getResource(emojiBean.getUrl(), mContext);
                if (resource != -1) {
                    viewHolder.iv.setImageResource(resource);
                }
            }
            return convertView;
        }
    }



    private void deleteEditText() {
        Editable editable = editText.getText();
        String s = editable.toString();
        if (s.endsWith("]")) {
            int i = s.lastIndexOf("[");
            editable.delete(i, s.length());
        }
    }


    static class ViewHolder {
        ImageView iv;
        LinearLayout rootView;

    }


//    private void showWinddowAbove(View view, int position) {
//        View contentView = LayoutInflater.from(mContext).inflate(
//                R.layout.emoji_pop_bg, null);
//        ImageView emojiView = contentView.findViewById(R.id.emoji_preview);
//        int resouceId = mEmojiList.get(position).getResouce();
//        if (resouceId != -1) {
//            emojiView.setImageResource(resouceId);
//        }
//
////        if (popupWindow == null) {  //先不做非空判断
//        popupWindow = new PopupWindow(contentView, Utils.Dp2Px(mContext, 56), Utils.Dp2Px(mContext, 118), true);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
////        }
//
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
//        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] + view.getHeight() - Utils.Dp2Px(mContext, 15) - popupWindow.getHeight());
//        //在控件的下方弹出窗口
//        //popupWindow.showAsDropDown(view);
//        //左边
//        //popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - view.getWidth(), location[1]);
//        //右边
//        //popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth(), location[1]);
//    }


}
