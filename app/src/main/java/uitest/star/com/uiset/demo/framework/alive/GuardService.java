package uitest.star.com.uiset.demo.framework.alive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import uitest.star.com.uiset.ProcessConnection;
import uitest.star.com.uiset.utils.ToastUtil;

/**
 * Created by yexing on 2019/3/15.
 * 守护进程，双进程通信
 */

public class GuardService extends Service {
    private static final int GUARD_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(GUARD_ID, new Notification());
        //绑定建立 连接
        bindService(new Intent(this, MessageService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ToastUtil.showApp("建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开连接，重新启动，重新绑定
            startService(new Intent(GuardService.this, MessageService.class));

            bindService(new Intent(GuardService.this, MessageService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
