package uitest.star.com.uiset.demo.framework.ioc;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import uitest.star.com.ioclibrary.ioc.CheckNet;
import uitest.star.com.ioclibrary.ioc.OnClick;
import uitest.star.com.ioclibrary.ioc.ViewById;
import uitest.star.com.ioclibrary.ioc.ViewUtils;
import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.utils.ToastUtil;

public class IocActivity extends UIActivity {

    @ViewById(R.id.tv)
    private AppCompatTextView mTextView;


    @Override
    protected int inflateLayout() {
        return R.layout.activity_ioc;
    }

    @Override
    protected void initialize() {
        ViewUtils.inject(this);
        mTextView.setText("IOC注解框架,请点击我！(详情看代码)");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv})
    @CheckNet
    //没网则不执行该方法 ,而是直接打印 没网的提示
    public void onClick(View view) {
        ToastUtil.showApp("你点击了我!");
    }


}
