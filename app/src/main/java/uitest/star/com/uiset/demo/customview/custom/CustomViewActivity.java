package uitest.star.com.uiset.demo.customview.custom;

import android.os.CountDownTimer;
import android.view.View;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.CountDownView;
import uitest.star.com.uiset.utils.ToastUtil;

public class CustomViewActivity extends UIActivity {


    @Override
    protected int inflateLayout() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        CountDownView countDown = findViewById(R.id.countDown);
        countDown.setTimeListener(new CountDownView.TimeListener() {
            @Override
            public void end() {
                ToastUtil.showApp("结束啦。。。");
            }

            @Override
            public void click() {
                ToastUtil.showApp("点击啦。。。");
            }
        });
    }

    @Override
    protected void initData() {
        Thread a = new AA("线程A");
        Thread a2 = new AA("线程B");
        a.start();
        a2.start();
    }

    class AA extends Thread {
        public AA(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "------------" + i);
                if (i == 4) {
                    System.out.println("线程让步");
                    Thread.yield();
                }
            }
        }
    }

}
