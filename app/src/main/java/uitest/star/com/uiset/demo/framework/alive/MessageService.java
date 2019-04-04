package uitest.star.com.uiset.demo.framework.alive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import uitest.star.com.uiset.ProcessConnection;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/3/15.
 */

public class MessageService extends Service {


    private int MESSAGE_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Log.e("ServiceTag", "等待接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(MESSAGE_ID, new Notification());  //也可以去掉
        //绑定建立 连接
        bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ToastUtil.showApp("建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开连接，重新启动，重新绑定
            //断开连接，重新启动，重新绑定
            startService(new Intent(MessageService.this, GuardService.class));

            bindService(new Intent(MessageService.this, GuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
