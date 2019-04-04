package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by yexing on 2019/1/10.
 */

public class VerticalDragListView extends FrameLayout {

    private ViewDragHelper viewDragHelper;
    private View mDragListView;
    private int mMenuHeight; //后面菜单的高度
    private float mDownY;
    private boolean mMenuisOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * @param changed 表示View有新的尺寸或者位置
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            View view = getChildAt(0);
            mMenuHeight = view.getMeasuredHeight();
        }
    }

    /**
     * 这个方法里拿不到控件宽高， 想要拿的话 一定是在测量完毕之后  #onMeasure 之后
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能包含两个子布局");
        }
        mDragListView = getChildAt(1);
    }

    /**
     * 拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //菜单打开要全部拦截
        if (mMenuisOpen) {
            return true;
        }

//        return viewDragHelper.shouldInterceptTouchEvent(ev);  //由工具类来决定是否要拦截子布局的触摸事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                //让viewDragHelper拿到完整的事件
                viewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - mDownY) > 0 && !canChildScrollUp()) {
                    //向上滑动 && 滚动到了最底部 ，拦截不让ListView处理
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mDragListView instanceof AbsListView) {
                AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getTop());
            } else {
                return ViewCompat.canScrollVertically(mDragListView, -1) || mDragListView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragListView, -1);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mDragListView == child;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            //垂直拖动的范围 只能是后面View的高度

            if (top < 0) {
                top = 0;
            } else if (top > mMenuHeight) {
                top = mMenuHeight;
            }
            return top;
        }


        /**
         * 列表只能垂直拖动
         */
//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return left;
//        }
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mDragListView.getTop() > mMenuHeight / 2) {
                //滑动到下方
                moveBottom();
                mMenuisOpen = true;
            } else {
                //滑动到最上面
                moveTop();
                mMenuisOpen = false;
            }
        }
    };

    private void moveTop() {
        int top = 0;
        moveContent(top);
    }

    private void moveBottom() {
        int top = mMenuHeight;
        moveContent(top);

    }

    private void moveContent(int top) {
        if (viewDragHelper.smoothSlideViewTo(mDragListView, 0, top)) {
            invalidate();
        }
//        viewDragHelper.settleCapturedViewAt(0,top);  //  看需求 ，不一定需要平滑 滚回去
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
