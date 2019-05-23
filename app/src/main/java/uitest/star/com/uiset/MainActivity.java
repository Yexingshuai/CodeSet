package uitest.star.com.uiset;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.demo.customview.Loading.LoadingViewActivity;
import uitest.star.com.uiset.demo.customview.anim58.Anim58Activity;
import uitest.star.com.uiset.demo.customview.car.CarHomeActivity;
import uitest.star.com.uiset.demo.customview.colortrack.ColorTrackActivity;
import uitest.star.com.uiset.demo.customview.custom.CustomViewActivity;
import uitest.star.com.uiset.demo.customview.flow.FlowLayoutActivity;
import uitest.star.com.uiset.demo.customview.letter.LetterSideActivity;
import uitest.star.com.uiset.demo.customview.menu.MenuActivity;
import uitest.star.com.uiset.demo.customview.navigation.BottomNavigationViewActivity;
import uitest.star.com.uiset.demo.customview.parallax.ParallaxActivity;
import uitest.star.com.uiset.demo.customview.qqslide.QQSlideActivity;
import uitest.star.com.uiset.demo.customview.qqstep.QQStepViewActivity;
import uitest.star.com.uiset.demo.customview.ratingbar.RatingBarActivity;
import uitest.star.com.uiset.demo.customview.red.RedPackageActivity;
import uitest.star.com.uiset.demo.customview.side.SidePullDelActivity;
import uitest.star.com.uiset.demo.customview.yahu.YahuActivity;
import uitest.star.com.uiset.demo.framework.alive.KeepAliveServiceActivity;
import uitest.star.com.uiset.demo.framework.aop.AopActivity;
import uitest.star.com.uiset.demo.framework.event.EventDispatchActivity;
import uitest.star.com.uiset.demo.framework.handler.HandlerActivity;
import uitest.star.com.uiset.demo.framework.ioc.IocActivity;
import uitest.star.com.uiset.demo.framework.notification.NotificationBarAdaptationActivity;
import uitest.star.com.uiset.demo.framework.service.ServiceActivity;
import uitest.star.com.uiset.demo.framework.state.StateModeActivity;
import uitest.star.com.uiset.demo.framework.strategy.StrategyModeActivity;
import uitest.star.com.uiset.demo.view.bottom.BottomSheetDialogActivity;
import uitest.star.com.uiset.demo.view.dialog.CommonDialogActivity;
import uitest.star.com.uiset.demo.view.dropdown.DropdownViewActivity;
import uitest.star.com.uiset.demo.view.material.MaterialDesignActivity;
import uitest.star.com.uiset.demo.view.okdialog.DialogActivity;
import uitest.star.com.uiset.demo.view.toolbar.ToolBarActivity;
import uitest.star.com.uiset.demo.view.ui.AppUIActivity;
import uitest.star.com.uiset.entity.Tab;
import uitest.star.com.uiset.ui.adapter.MyFragmentPagerAdapter;

public class MainActivity extends UIActivity {


    private TabLayout mTabLayout;//789
    private ViewPager mViewPager;
    private List<Tab> mCustomViewList = new ArrayList<>();
    private List<Tab> mViewList = new ArrayList<>();
    private List<Tab> mFrameWorkList = new ArrayList<>();
    private String[] mTitle = {"自定义View", "控件", "架构"};
    private List[] mList = {mCustomViewList, mViewList, mFrameWorkList};//In dev


    @Override
    protected int inflateLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {

        //  自定义View
        mCustomViewList.add(new Tab("QQStepView", QQStepViewActivity.class));
        mCustomViewList.add(new Tab("字体变色", ColorTrackActivity.class));
        mCustomViewList.add(new Tab("58同城动画", Anim58Activity.class));
        mCustomViewList.add(new Tab("自定义View", CustomViewActivity.class));
        mCustomViewList.add(new Tab("RatingBar", RatingBarActivity.class));
        mCustomViewList.add(new Tab("QQ侧滑", QQSlideActivity.class));
        mCustomViewList.add(new Tab("雅虎视差特效", YahuActivity.class));
        mCustomViewList.add(new Tab("酷狗音乐引导页", ParallaxActivity.class));
        mCustomViewList.add(new Tab("字母索引指示器", LetterSideActivity.class));
        mCustomViewList.add(new Tab("仿汽车之家", CarHomeActivity.class));
        mCustomViewList.add(new Tab("LoadingView", LoadingViewActivity.class));
        mCustomViewList.add(new Tab("FlowLayout", FlowLayoutActivity.class));
        mCustomViewList.add(new Tab("侧拉删除", SidePullDelActivity.class));
        mCustomViewList.add(new Tab("抢红包特效", RedPackageActivity.class));
        mCustomViewList.add(new Tab("菜单筛选", MenuActivity.class));

        //控件
        mViewList.add(new Tab("BottomNav", BottomNavigationViewActivity.class));
        mViewList.add(new Tab("DropDownView", DropdownViewActivity.class));
        mViewList.add(new Tab("万能Dialog", DialogActivity.class));
        mViewList.add(new Tab("Builder标题栏", ToolBarActivity.class));
        mViewList.add(new Tab("BottomSheetDialog", BottomSheetDialogActivity.class));
        mViewList.add(new Tab("App常用UI", AppUIActivity.class));
        mViewList.add(new Tab("常用对话框", CommonDialogActivity.class));
        mViewList.add(new Tab("Material Design", MaterialDesignActivity.class));

        //架构
        mFrameWorkList.add(new Tab("AOP切面编程", AopActivity.class));
        mFrameWorkList.add(new Tab("状态模式", StateModeActivity.class));
        mFrameWorkList.add(new Tab("事件分发", EventDispatchActivity.class));
        mFrameWorkList.add(new Tab("手写Handler", HandlerActivity.class));
        mFrameWorkList.add(new Tab("IOC注解框架", IocActivity.class));
        mFrameWorkList.add(new Tab("策略设计模式", StrategyModeActivity.class));
        mFrameWorkList.add(new Tab("Service保活", KeepAliveServiceActivity.class));
        mFrameWorkList.add(new Tab("Service", ServiceActivity.class));
        mFrameWorkList.add(new Tab("8.0通知适配", NotificationBarAdaptationActivity.class));


        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mTitle, mList));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

    }


}
