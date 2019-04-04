package uitest.star.com.uiset.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import uitest.star.com.uiset.demo.customview.menu.BaseMenuAdapter;

/**
 * Created by yexing on 2019/1/23.
 */

public class ListDataScreenView extends LinearLayout implements View.OnClickListener {

    private static final int DURATION_TIME = 300;
    private Context mContext;

    private LinearLayout mMenuTabView; //存放Tab

    private FrameLayout mMenuMiddleView;  //菜单内部布局和阴影

    private View mShadowView; //阴影

    //    private int mShadowColor = Color.parseColor("0x88888888"); //阴影颜色
    private int mShadowColor = 0x88888888; //阴影颜色

    private FrameLayout mMenuContainerView;//创建菜单 用来存放菜单内容
    //筛选菜单的adapter
    private BaseMenuAdapter mAdapter;
    private int mMenuContainerHeight;

    private boolean mAnimatorExecute;//动画是否在执行


    private int mCurrentPosition = -1; //当前的打开位置

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    /**
     * 布局实例化
     */
    private void initLayout() {

        setOrientation(VERTICAL);

        //创建头部 存放Tab
        mMenuTabView = new LinearLayout(mContext);
        mMenuTabView.setOrientation(HORIZONTAL);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);

        //用来存放  阴影+菜单内容布局
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mMenuMiddleView = new FrameLayout(mContext);
        mMenuMiddleView.setLayoutParams(params);
        addView(mMenuMiddleView);

        //阴影
        mShadowView = new View(mContext);//不用设置layoutParams 默认就是match， match
        mShadowView.setBackgroundColor(mShadowColor);
        mShadowView.setAlpha(0);  //一上来不显示阴影
        mShadowView.setOnClickListener(this);    //点击阴影 也要关闭
        mShadowView.setVisibility(View.GONE);
        mMenuMiddleView.addView(mShadowView);

        //创建菜单
        mMenuContainerView = new FrameLayout(mContext);
        mMenuContainerView.setBackgroundColor(Color.WHITE);
        mMenuContainerView.setClickable(true);
        mMenuMiddleView.addView(mMenuContainerView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //内容高度不是全部  百分比 六七十左右

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (mMenuContainerHeight == 0 && height > 0) {
            mMenuContainerHeight = (int) (75f / 100f * height);
            ViewGroup.LayoutParams parms = mMenuContainerView.getLayoutParams();
            parms.height = mMenuContainerHeight;
            mMenuContainerView.setLayoutParams(parms);

            //如果在这个地方 setMeasureDimension  的话 ，那么黑屏的距离 也会减少，不能这样！！！

            //进来时候 阴影不显示 内容不显示，把他移上去
            mMenuContainerView.setTranslationY(-mMenuContainerHeight);
        }


    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(BaseMenuAdapter adapter) {
        this.mAdapter = adapter;
        //获取条目
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            //获取Tab
            View tabView = mAdapter.getTabView(i, mMenuTabView);
            mMenuTabView.addView(tabView);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) tabView.getLayoutParams();
            layoutParams.weight = 1;
            tabView.setLayoutParams(layoutParams);

            //设置点击事件
            setTabClick(tabView, i);

            //获取菜单内容
            View menuView = mAdapter.getMenuView(i, mMenuContainerView);
            menuView.setVisibility(View.GONE); //点击下拉按钮时候 才打开
            mMenuContainerView.addView(menuView);
        }

        //进来时候 阴影不能显示 ,内容也不显示
        //阴影点击 关闭菜单
        //打开菜单 显示当前位置的菜单，
        //打开关闭，tab的变化


    }

    /**
     * 设置Tab点击事件
     *
     * @param tabView
     * @param i
     */
    private void setTabClick(final View tabView, final int i) {
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPosition == -1) { //没有打开
                    openMenu(i, tabView);
                } else {

                    if (mCurrentPosition == i) {
                        //已打开
                        closeMenu();
                    } else {
                        //切换显示  ,不在执行动画
                        View currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(GONE);
                        mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
                        mCurrentPosition = i;
                        currentMenu = mMenuContainerView.getChildAt(mCurrentPosition);
                        currentMenu.setVisibility(VISIBLE);
                        mAdapter.menuOpen(mMenuTabView.getChildAt(mCurrentPosition));

                    }
                }
            }
        });
    }

    //关闭
    private void closeMenu() {

        if (mAnimatorExecute) {
            return;
        }

        //执行开启动画  位移  透明度
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0, -mMenuContainerHeight);

        mShadowView.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(translationAnim, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                View menuView = mMenuContainerView.getChildAt(mCurrentPosition);
                menuView.setVisibility(View.GONE);
                mCurrentPosition = -1;
                mShadowView.setVisibility(GONE);
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimatorExecute = true;
                mAdapter.menuClose(mMenuTabView.getChildAt(mCurrentPosition));
            }
        });
        animatorSet.start();
        //要等动画执行完后才能去隐藏当前菜单


    }

    //打开
    private void openMenu(final int position, final View tabView) {
        if (mAnimatorExecute) {
            return;
        }

        mShadowView.setVisibility(View.VISIBLE);
        //获取当前位置 显示当前菜单
        View menuView = mMenuContainerView.getChildAt(position);
        menuView.setVisibility(View.VISIBLE);


        //执行开启动画  位移  透明度
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.playTogether(translationAnim, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorExecute = false;
                mCurrentPosition = position;

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimatorExecute = true;
                //把Tab传到外面
                mAdapter.menuOpen(tabView);
            }
        });
        animatorSet.start();


    }

    @Override
    public void onClick(View view) {
        closeMenu();
    }
}
