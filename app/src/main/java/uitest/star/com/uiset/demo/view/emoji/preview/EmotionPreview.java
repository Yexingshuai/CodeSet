package uitest.star.com.uiset.demo.view.emoji.preview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.demo.view.emoji.entity.EmojiBean;
import uitest.star.com.uiset.demo.view.emoji.utils.EmojiUtil;
import uitest.star.com.uiset.utils.UiUtils;

public class EmotionPreview implements AdapterView.OnItemLongClickListener, View.OnTouchListener {
    private static int VIEW_SIZE;
    private static int VIEW_HEIGHT;

    private final int[] gvLoc = new int[2];
    private GridView vCurGrid;
    private int parentWidth;
    private int childWidth;
    private int childHeight;
    private Context mContext;
    private final List<Pair<Integer, Point>> gifInfos = new ArrayList<>();
    private WindowManager wm = null;
    private final WindowManager.LayoutParams layoutParams;
    private GifView vGif;

    public EmotionPreview(Context context) {
        this.mContext = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.width = UiUtils.dp2px(56);
        layoutParams.height = UiUtils.dp2px(118);
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        VIEW_SIZE = UiUtils.dp2px(56);
        VIEW_HEIGHT = UiUtils.dp2px(118);
    }

    /**
     * 长按事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        EmojiBean emojiBean = (EmojiBean) parent.getItemAtPosition(position);
        int resID = EmojiUtil.getResource(emojiBean.getUrl(), mContext);
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);


        vCurGrid = (GridView) parent;
        parent.getLocationOnScreen(gvLoc);

        parentWidth = parent.getWidth();
        childWidth = view.getWidth();
        childHeight = view.getHeight();

        int start = parent.getFirstVisiblePosition();
        int end = parent.getLastVisiblePosition();

        if (position != end & resID != -1) { //如果是最后一个，说明是删除按钮
            showGif(resID, loc);
        }

        for (int i = 0; i < end - start; i++) {
            View v = parent.getChildAt(i);
            if (v == null) {
                continue;
            }
            v.getLocationOnScreen(loc);
            EmojiBean emojiBean1 = (EmojiBean) parent.getItemAtPosition(i + start);
            int resId = EmojiUtil.getResource(emojiBean1.getUrl(), mContext);
            Point point = new Point(loc[0], loc[1]);
            Pair<Integer, Point> pair = new Pair<>(resId, point);
            gifInfos.add(pair);
        }

        return true;
    }


    private void showGif(int resId, int[] loc) {

        if (vGif == null) {
            vGif = new GifView(mContext);
            vGif.setResourceId(resId);
        }
        updateLayoutParams(loc);
        if (vGif.getParent() == null) {
            wm.addView(vGif, layoutParams);
        }
        vGif.setTag(resId);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (vGif == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                vCurGrid.getParent().requestDisallowInterceptTouchEvent(true);
                float rawX = event.getRawX();
                float rawY = event.getRawY();

                int size = gifInfos.size();
                boolean findTarget = false;
                for (int i = 0; i < size; i++) {
                    Pair<Integer, Point> info = gifInfos.get(i);
                    Point loc = info.second;
                    if (rawX >= loc.x && rawX <= loc.x + childWidth
                            && rawY > loc.y && rawY < loc.y + childHeight) {
                        if (info.first.equals(vGif.getTag())) {
                            // 如果是正在播放的gif则返回
                            return false;
                        }

                        findTarget = true;
                        updateGif(info.first, new int[]{info.second.x, info.second.y});
                        break;
                    }
                }
                if (!findTarget) {
                    pauseGif();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                vCurGrid.getParent().requestDisallowInterceptTouchEvent(false);
                hideGif();
                gifInfos.clear();
                break;
            default:
                break;
        }

        return true;
    }

    private void pauseGif() {
        if (vGif.getParent() != null) {
            wm.removeView(vGif);
        }
        vGif.setTag(null);
    }

    private void hideGif() {
        if (vGif.getParent() != null) {
            wm.removeView(vGif);
        }
        vGif = null;
        vCurGrid = null;
    }

    private void updateGif(int resId, int[] loc) {
        if (resId == -1) {
            return;
        }
        updateLayoutParams(loc);
        if (vGif.getParent() == null) {
            wm.addView(vGif, layoutParams);
        } else {
            wm.updateViewLayout(vGif, layoutParams);
        }
        vGif.setResourceId(resId);
        vGif.setTag(resId);
    }


    private void updateLayoutParams(int[] location) {
        layoutParams.y = location[1] - VIEW_HEIGHT + 50;
        layoutParams.x = location[0] + childWidth / 2 - VIEW_SIZE / 2;
        if (layoutParams.y < 0) {
            layoutParams.y = 0;
        }
        if (layoutParams.x < 0) {
            layoutParams.x = 0;
        }
        if (layoutParams.x > parentWidth - VIEW_SIZE / 2) {
            layoutParams.x = parentWidth - VIEW_SIZE / 2;
        }
    }

}
