package uitest.star.com.uiset.demo.customview.flow;

import android.widget.TextView;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.ui.widget.FlowLayout;
import uitest.star.com.uiset.utils.UiUtils;

public class FlowLayoutActivity extends UIActivity {


    private FlowLayout flowlayout;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_flow_layout;
    }


    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        flowlayout = findViewById(R.id.flowlayout);
    }

    @Override
    protected void initData() {
        String[] stringArray = getResources().getStringArray(R.array.Lines);
        for (String s : stringArray) {
            TextView textView = UiUtils.createRandomColorSelectorTextView();
            textView.setText(s);
            flowlayout.addView(textView);
        }
//
//
//        Log.e("sfsdaf", flowlayout.getChildAt(7).getBottom() + "--------" + flowlayout.getChildAt(7).getTop() + "--------");


    }


}
