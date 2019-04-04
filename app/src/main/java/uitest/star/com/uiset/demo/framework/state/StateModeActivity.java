package uitest.star.com.uiset.demo.framework.state;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.base.UIActivity;
import uitest.star.com.uiset.demo.framework.state.state.LoginState;
import uitest.star.com.uiset.demo.framework.state.state.LogoutState;

public class StateModeActivity extends UIActivity implements View.OnClickListener {


    private TextView mCurrentState;  //当前登录状态
    private Button mLogin;
    private Button mExit;  //退出登录
    private Button mForward;
    private Button mCommit;
    private LoginContext mLoginContext;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_state_mode;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mCurrentState = findViewById(R.id.tv5);
        mCurrentState.setOnClickListener(this);
        mLogin = findViewById(R.id.bt_login);
        mLogin.setOnClickListener(this);
        mExit = findViewById(R.id.bt_exit);
        mExit.setOnClickListener(this);
        mForward = findViewById(R.id.bt_forward);
        mForward.setOnClickListener(this);
        mCommit = findViewById(R.id.bt_commit);
        mCommit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mLoginContext = LoginContext.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startActivityForResult(new Intent(StateModeActivity.this, LoginActivity.class), 1001);
                break;
            case R.id.bt_exit:
                mLoginContext.setState(new LogoutState());
                mCurrentState.setText("未登录");
                break;
            case R.id.bt_forward:
                mLoginContext.forward(StateModeActivity.this);
                break;
            case R.id.bt_commit:
                mLoginContext.commit(StateModeActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLoginContext != null) {
            if (mLoginContext.getState() instanceof LoginState) mCurrentState.setText("已登录");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == 1002) {
                mLoginContext.setState(new LoginState());
                mCurrentState.setText("已登录");
            }

        }
    }
}
