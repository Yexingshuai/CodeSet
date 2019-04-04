package uitest.star.com.uiset.demo.view.ui;


import android.widget.Button;
import android.widget.EditText;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.helper.InputTextHelper;
import uitest.star.com.uiset.utils.StatusBarUtil;

public class LoginActivity extends UIActivity {


    private EditText mPhoneView;
    private EditText mPasswordView;
    private Button mCommitView;
    private InputTextHelper mInputTextHelper;

    @Override
    protected int inflateLayout() {
        return R.layout.uiactivity_login;
    }

    @Override
    protected void initialize() {
        StatusBarUtil.setStatusBarColor2(this, false);
    }

    @Override
    protected void initView() {
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCommitView = findViewById(R.id.btn_login_commit);
        mInputTextHelper = new InputTextHelper(mCommitView);
        mInputTextHelper.addViews(mPhoneView, mPasswordView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
