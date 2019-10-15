package uitest.star.com.uiset;

import android.app.Application;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import uitest.star.com.uiset.utils.ToastUtil;

public class EnviromentConfig {

    public static void init(Application application) {
        Properties props = new Properties();
        InputStream is = null;
        String net_env = null;
        try {
            is = application.getAssets().open("CodeSet.properties");
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
//            throw new RuntimeException("Properties error..");
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
            ToastUtil.showApp("请检查你的网络配置是否正确！");
        }

    }
}
