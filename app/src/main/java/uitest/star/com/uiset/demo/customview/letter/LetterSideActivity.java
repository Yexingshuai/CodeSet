package uitest.star.com.uiset.demo.customview.letter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.LetterSideBar;

public class LetterSideActivity extends UIActivity {


    @Override
    protected int inflateLayout() {
        return R.layout.activity_letter_side;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        final TextView tv = findViewById(R.id.tv);
        LetterSideBar letterSideBar = findViewById(R.id.letter_sidebar);
        letterSideBar.setLetterSideBarListener(new LetterSideBar.LetterSideBarListener() {
            @Override
            public void touchText(String s) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(s);
            }

            @Override
            public void dismiss() {
                tv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initData() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.e("qaz", valueAnimator.getAnimatedValue() + "");
            }
        });

        valueAnimator.start();
    }
}
