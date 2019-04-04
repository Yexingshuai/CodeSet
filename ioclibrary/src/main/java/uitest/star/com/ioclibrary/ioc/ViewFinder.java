package uitest.star.com.ioclibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by yexing   on 2019/3/1.
 * View 的 findViewById 的辅助类
 */

public class ViewFinder {
    private Activity mActivity;

    private View mView;


    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);

    }


}
