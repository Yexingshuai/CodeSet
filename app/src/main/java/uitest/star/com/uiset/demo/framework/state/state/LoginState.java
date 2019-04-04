package uitest.star.com.uiset.demo.framework.state.state;

import android.content.Context;

import uitest.star.com.uiset.demo.framework.state.UserState;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/1/17.
 */

public class LoginState  implements UserState{
    @Override
    public void forword(Context context) {
        ToastUtil.showApp("转发成功！");
    }

    @Override
    public void commit(Context context) {
        ToastUtil.showApp("评论成功！");
    }
}
