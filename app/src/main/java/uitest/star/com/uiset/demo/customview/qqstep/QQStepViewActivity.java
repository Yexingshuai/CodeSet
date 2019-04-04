package uitest.star.com.uiset.demo.customview.qqstep;

import android.view.View;
import android.widget.Button;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.QQStepView;

public class QQStepViewActivity extends UIActivity {


    private Button bt;
    private QQStepView qqStepView;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_qqstep_view;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        bt = findViewById(R.id.bt);
        qqStepView = findViewById(R.id.stepView);
    }

    @Override
    protected void initData() {


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqStepView.setCurrentStep(500);
            }
        });
    }
}
