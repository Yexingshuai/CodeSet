package uitest.star.com.uiset.demo.framework.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/3/26.
 * Start方式开启 Service的时候 只会执行onCreate()和onStartCommand
 * 多次开启只会执行
 * <p>
 * Bind方式：  绑定服务会执行onCreate 和 onBind
 * 服务只能绑定一次
 * 绑定服务跟着Activity的销毁 跟着销毁
 * 绑定服务 不能多次解绑
 *
 *  * aidl  安卓接口定义语言
 * 1.把接口文件  后缀名改成.aidl
 * 2.解决报错， 把java里面的权限修饰符去掉，因为aidl默认都是public
 * 3.把Service里面的binder的继承关系改变，改成继承Stub
 * 4.本地服务必须要有一个和远程服务一样的.aidl文件
 * 5.要把.aidl的包名改成远程服务里面的包名
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TAG", "onBind.....");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("TAG", "onUnbind.....");
        return super.onUnbind(intent);

    }

    /**
     * 只有第一次开启才会执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate.....");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    Log.e("TAG", "SERVICE.....");
//                }
//
//            }
//        }).start();
    }


    /**
     * Bind 方式没有 此方法!!!!
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStartCommand.....");
        return super.onStartCommand(intent, flags, startId);
    }

    public void showToasts() {
        ToastUtil.showApp("toast...");
    }

    private class MyBinder extends Binder implements IService{

        public void showToast() {
            showToasts();
        }

    }
}
