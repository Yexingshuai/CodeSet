package uitest.star.com.uiset.ui.widget;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.FrameLayout;

/**
 * Created by yexing on 2019/1/8.
 * <p>
 * QQ侧滑
 */

public class DragLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private ViewGroup mLeft;
    private ViewGroup mContent;
    private int mRange;
    private int mWidth;
    private int mHeight;
    private ArgbEvaluator argbEvaluator;
    private Drawable background;
    private Status mStatus;
    private OnDragStatusChangeListener mListener;


    public enum Status {

        CLOSED, OPENED, DRAGING;
    }

    public Status getStatus() {
        return mStatus;
    }


    public DragLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        //分析触摸事件工具类  参1:需要交给ViewDragHelper分析的控件,  参2：手势敏感度  参3：手势分析出来的状态回调
        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyCallback());

        argbEvaluator = new ArgbEvaluator();
        //背景
        background = getBackground();

    }


    /**
     * 向子布局分发触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);  //由工具类来决定是否要拦截子布局的触摸事件
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);  //工具类来分析是否要拖拽子布局，以及拖拽到的位置
        return true;
    }


    /**
     * 当前控件生成出来的的时候  调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("DragLayout 只能放置两个子控件!");
        }

        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new RuntimeException("DragLayout  只能放置ViewGroup");
        }

        //获取子控件
        mLeft = (ViewGroup) getChildAt(0);
        mContent = (ViewGroup) getChildAt(1);
    }

    /**
     * 当控件大小发生变化时 调用
     * 在 # onMeasure之后， #onLayout()之前执行
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取控件宽高，计算面板的最大展开范围
        mWidth = w;
        mHeight = h;
        mRange = (int) (w * 0.6f);
    }

    private class MyCallback extends ViewDragHelper.Callback {


        /**
         * 尝试捕捉子控件
         *
         * @param child     当前手势触摸到的子控件
         * @param pointerId 多指触控时的手指id
         * @return 返回true  就表示child 允许被拖拽    -----------------------
         */
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }


        /**
         * 固定子控件的水平位置  ,此方法在view移动之前 调用
         *
         * @param child
         * @param left  view距离屏幕左侧距离   (系统给我们推荐移动到的位置)
         * @param dx    child的上一次位置，到新推荐位置的偏移大小
         * @return 根据需求 修正后的移动位置
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

            //限制正文部分移动范围
            if (child == mContent) {
                if (left >= mRange) {
                    left = mRange;
                } else if (left < 0) {
                    left = 0;
                }
            }

            return left;
        }

        /**
         * 固定子控件的垂直位置
         *
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        /**
         * 当被拖拽的控件已经移动过后会调用这个方法  真实移动之后执行l         重要：！！！！！ 移动过后
         *
         * @param changedView
         * @param left        正八经移动到的位置
         * @param top
         * @param dx          距离上一次真实位置之间的偏移大小  总是为正数
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);  //可以删

            if (changedView == mLeft) {
                mLeft.offsetLeftAndRight(-dx);

                int oldLeft = mContent.getLeft();     //这个oldLeft有可能为负数

                int newLeft = oldLeft + dx;  //新的左侧位置
                if (newLeft > mRange) {
                    newLeft = mRange;
                } else if (newLeft < 0) {
                    newLeft = 0;
                }

                dx = newLeft - oldLeft;
                mContent.offsetLeftAndRight(dx);
            }

            //处理联动效果
            handlerAnimator();
        }


        /**
         * 当用户松手时被调用
         *
         * @param releasedChild
         * @param xvel          当松手时正在向右滑动则为正值,向左滑动则为负值，静止不动则为0
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);  //可删除

            if (xvel > 0) {
                //向右滑动, 打开面板
                open();
            } else if (xvel < 0) {
                //向左滑动，关闭面板
                close();
            } else {
                //静止不动
                if (mContent.getLeft() > mRange / 2) {
                    //面板展开超过一半,打开面板
                    open();
                } else {
                    //面板展开不到一半，关闭面板
                    close();
                }

            }
        }

        /**
         * 返回大于0,可以让侧拉面板拦截子布局的水平滑动事件
         *
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 1;
        }


        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return super.getViewVerticalDragRange(child);
        }
    }

    private void handlerAnimator() {
        //最终值=起始值-变化值
        //变化值=百分比*变化范围
        //变化范围=起始值-结束值
        //百分比=正文左侧位置/最大展开大小
        //百分比
        float percent = mContent.getLeft() / (float) mRange;

        //变化范围
        float startScale = 1.0f;
        float endScale = 0.8f;
        float scaleRange = startScale - endScale;

        //变化值
        float offsetScale = percent * scaleRange;

        //最终值
        float contentScale = startScale - offsetScale;

        //处理正文的缩放效果
        mContent.setScaleX(contentScale);
        mContent.setScaleY(contentScale);


        //侧栏的缩放

        float leftScale = evaluate(percent, 0.5f, 1.0f);
        mLeft.setScaleX(leftScale);
        mLeft.setScaleY(leftScale);

        //平移侧栏面板
        float leftTranslationX = evaluate(percent, -mRange, 0f);
        mLeft.setTranslationX(leftTranslationX);

        //修改侧栏的透明度
        float leftAlpha = evaluate(percent, 0f, 1f);
        mLeft.setAlpha(leftAlpha);

        //背景
        int evaluateColor = (int) argbEvaluator.evaluate(percent, Color.BLACK, Color.TRANSPARENT);
        background.setColorFilter(evaluateColor, PorterDuff.Mode.SRC_OVER);

        //处理监听
        handlerListener(percent);

    }

    private void handlerListener(float percent) {
        if (mListener == null) {
            return;
        }

        mListener.draging(percent);
        if (percent == 1) {
            mStatus = Status.OPENED;
            mListener.opened();
        } else if (percent == 0) {
            mStatus = Status.CLOSED;
            mListener.closed();
        } else {
            mStatus = Status.DRAGING;
        }
    }

    /**
     * 在起始值和结束值之间，根据百分比计算出一个中间值
     *
     * @return
     */
    private float evaluate(float fraction, float startValue, float endValue) {
        return startValue + fraction * (endValue - startValue);
    }

    private void close() {
        int left = 0;
        moveContent(left);
    }

    private void open() {
        int left = mRange;
        moveContent(left);
    }

    /**
     * 正文部分移动到指定位置
     *
     * @param left
     */
    private void moveContent(int left) {
        //静态的修改
        // mContent.layout(left, 0, mWidth + left, mHeight);

        //平滑的滚动正文位置,(一帧一帧的),  如果返回true，表示控件还没有滚动到指定位置，需要继续滚动
        if (mDragHelper.smoothSlideViewTo(mContent, left, 0)) {
            //他的返回值是boolean，表示他是否滚动到指定位置};
            invalidate();
        }
    }


    /**
     * 上面的smoothSlideViewTo会来到这个方法, 会被多次调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //移动一次子控件的位置，如果返回true，说明还没有移动到指定位置，需要继续移动
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setOnDragStatusChangeListener(OnDragStatusChangeListener onDragStatusChangeListener) {
        mListener = onDragStatusChangeListener;
    }

    public interface OnDragStatusChangeListener {

        void closed();

        void opened();

        void draging(float percent);
    }

}
