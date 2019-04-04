package uitest.star.com.uiset.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import uitest.star.com.uiset.R;

/**
 * Created by yexing on 2019/1/15.
 */

public class SwipeLayout extends FrameLayout implements View.OnClickListener {

    private ViewDragHelper mViewDragHelper;
    private View mContent;
    private View mDeletePanel;
    private int mWidth;
    private int mHeight;
    private int mRange;
    private Status status = Status.CLOSED;
    private static SwipeLayout preSwipeLayout; //记录上一个打开的面板
    private onDeletePanelClickListener mListener;


    private enum Status {
        CLOSED, OPENED, DRAGING;
    }

    public Status getStatus() {
        return status;
    }

    public SwipeLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /***
     * 分析用户手势
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    /**
     * 可获取子控件   OnMeasure， onLayout之前
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("SwipeLayout 必须存放两个子控件");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new RuntimeException("SwipeLayout 的子控件必须是ViewGroup");
        }
        mContent = getChildAt(0);
        mDeletePanel = getChildAt(1);
        TextView mTvCall = mDeletePanel.findViewById(R.id.tv_call);
        mTvCall.setOnClickListener(this);
        TextView mTvDel = mDeletePanel.findViewById(R.id.tv_Del);
        mTvDel.setOnClickListener(this);
    }


    /**
     * onMeasure 之后，onLayout之前
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //按钮移动范围
        mRange = mDeletePanel.getMeasuredWidth();   //记住一定是  measureWidth　！！！！！！！！！！！！！！！！！
    }

    /**
     * 把侧栏部分放到最右边
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //把侧栏部分放到最右边

        mDeletePanel.layout(mWidth, 0, mWidth + mRange, mHeight);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        //返回true, child就能拖拽
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

            if (mContent == child) {
                if (left > 0) {
                    left = 0;
                } else if (left < -mRange) {
                    left = -mRange;
                }
            } else {
                if (left > mWidth) {
                    left = mWidth;
                } else if (left < mWidth - mRange) {
                    left = mWidth - mRange;
                }
            }
            return left;
        }

        /**
         * 当被拖拽的控件已经移动过后，就会调用这个方法
         * @param changedView
         * @param left        被拖拽控件移动到的真实位置
         * @param top
         * @param dx          被拖拽控件的真实偏移大小
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mContent) {
                mDeletePanel.offsetLeftAndRight(dx);
            } else {
                // 移动侧栏,
                mContent.offsetLeftAndRight(dx);
            }

            //关闭上一个打开的面板,
            closePre();
//
//            invalidate();//系统bug ，有时候拖拽控件，界面不会刷新，需要手动刷新
        }


        /**
         * 松手的时候
         * @param releasedChild
         * @param xvel      如果是正数，向右移动。  代表速度
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {


            if (xvel > 0) {
                //向右移动，关闭面板
                close();
            } else if (xvel < 0) {
                //向左移动，打开面板
                open();
            } else {
                //静止状态
                if (mContent.getLeft() > -mRange / 2) {
                    //展开不到一半，关闭面板
                    close();
                } else {
                    open();
                }
            }
        }
    };

    /**
     * 判断当前面板是否正在打开，如果是 将上一个打开的面板关闭
     */
    private void closePre() {

        //记录旧状态
        Status preStatus = status;

        //获取面板状态
        if (mContent.getLeft() == -mRange) {
            status = Status.OPENED;
        } else if (mContent.getLeft() == 0) {
            status = Status.CLOSED;
        } else {
            status = Status.DRAGING;
        }

        //如果旧状态是关闭，并且新状态为拖拽，此时就可以关闭之前打开的面板
        if (preStatus == Status.CLOSED && status == Status.DRAGING) {
            if (preSwipeLayout != null && preSwipeLayout != this) {
                //关闭上一个面板
                preSwipeLayout.close();
            }
            //将当前面板 标记为打开的面板
            preSwipeLayout = this;

        }
    }


    private void open() {
        int left = -mRange;
        moveContent(left);
    }

    private void close() {
        int left = 0;
        moveContent(left);
    }

    private void moveContent(int left) {

        //这种方式 移动太突兀
//        mContent.layout(left, 0, left + mWidth, mHeight);
//
//        int deleteLeft = left + mWidth;
//        mDeletePanel.layout(deleteLeft, 0, deleteLeft + mRange, mHeight);

        //平滑滚动       返回true说明没有滚动完
        if (mViewDragHelper.smoothSlideViewTo(mContent, left, 0)) {
            invalidate();  //会激活下面的computeScroll方法
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 返回true说明没有滚动完
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }

    }

    public void setOnDeletaPanelClickListener(onDeletePanelClickListener onDeletaPanelClickListener) {

        mListener = onDeletaPanelClickListener;

    }

    public interface onDeletePanelClickListener {

        void clickCall();

        void clickDel();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call:
                mListener.clickCall();
                break;
            case R.id.tv_Del:
                mListener.clickDel();
                break;
        }
    }

}
