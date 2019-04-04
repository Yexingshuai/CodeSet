package uitest.star.com.uiset.demo.framework.state;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.BaseActivity;
import uitest.star.com.uiset.demo.framework.state.state.LoginState;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/1/17.
 */

public class LoginActivity extends BaseActivity {

    private Button mLogin;

    private ProgressDialog progressDialog;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        mLogin = findViewById(R.id.bt_login);
    }

    @Override
    protected void initData() {
        progressDialog = new ProgressDialog(this);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("请稍等");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        ToastUtil.showApp("登录成功！");
                        LoginContext.getInstance().setState(new LoginState());
                        setResult(1002);
                        finish();
                    }
                }, 3000);
            }
        });
    }
}
