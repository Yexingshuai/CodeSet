package uitest.star.com.uiset.demo.framework.state;

import android.content.Context;

/**
 * Created by yexing on 2019/1/17.
 */

public interface UserState {


    /**
     * 转发操作
     *
     * @param context
     */
    void forword(Context context);

    /**
     * 评论操作
     *
     * @param context
     */
    void commit(Context context);
}
