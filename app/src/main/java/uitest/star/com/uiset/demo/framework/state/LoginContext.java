package uitest.star.com.uiset.demo.framework.state;

import android.content.Context;

import uitest.star.com.uiset.demo.framework.state.state.LogoutState;

/**
 * Created by yexing on 2019/1/17.
 */

public class LoginContext {


    private UserState state = new LogoutState();

    /**
     * 避免外界new对象
     */
    private LoginContext() {

    }

    public void setState(UserState state) {
        this.state = state;
    }

    public UserState getState() {
        return state;
    }

    //转发
    public void forward(Context context) {
        state.forword(context);
    }

    //评论
    public void commit(Context context) {
        state.commit(context);
    }


    public static LoginContext getInstance() {
        return Holder.instance;
    }


    private static final class Holder {
        private static final LoginContext instance = new LoginContext();
    }
}

