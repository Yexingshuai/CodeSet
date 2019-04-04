package uitest.star.com.uiset.demo.framework.state.state;

import android.content.Context;
import android.content.Intent;

import uitest.star.com.uiset.demo.framework.state.LoginActivity;
import uitest.star.com.uiset.demo.framework.state.UserState;

/**
 * Created by yexing on 2019/1/17.
 */

public class LogoutState implements UserState {


    /**
     * 必须先去登录
     *
     * @param context
     */
    @Override
    public void forword(Context context) {
        goLoginActivity(context);
    }


    /**
     * 必须先去登录
     *
     * @param context
     */
    @Override
    public void commit(Context context) {
        goLoginActivity(context);
    }

    private void goLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));

    }
}
