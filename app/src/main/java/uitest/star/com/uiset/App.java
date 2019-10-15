package uitest.star.com.uiset;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2018/12/19.
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ToastUtil.initToastApp(context);
        BGASwipeBackHelper.init(this, null);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


        Properties props = new Properties();
        InputStream is = null;
        String net_env = null;
        try {
            is = context.getAssets().open("CodeSet.properties");
            props.load(is);
            net_env = props.getProperty("host");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (TextUtils.isEmpty(net_env)) {
            throw new RuntimeException("Properties error..");
        }
        if (!TextUtils.isEmpty(net_env)) {
            if (TextUtils.equals(net_env, "release")) {
                ToastUtil.showApp("正式环境");
            } else if (TextUtils.equals(net_env, "debug")) {
                ToastUtil.showApp("测试环境");
            } else if (net_env.startsWith("192.168.1")) {
                ToastUtil.showApp(net_env);
            }
        } else {
            ToastUtil.showApp("net_env为空");
        }


    }

    public static Context getContext() {
        return context;
    }



}
