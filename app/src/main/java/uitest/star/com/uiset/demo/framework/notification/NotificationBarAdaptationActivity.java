package uitest.star.com.uiset.demo.framework.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;

/**
 * 如果你将项目中的targetSdkVersion指定到了26或者更高，那么Android系统就会认为你的App已经做好了8.0系统的适配工作，
 * 当然包括了通知栏的适配。这个时候如果还不使用通知渠道的话，那么你的App的通知将完全无法弹出
 */
public class NotificationBarAdaptationActivity extends UIActivity {
    @Override
    protected int inflateLayout() {
        return R.layout.activity_notificationbar;
    }

    @Override
    protected void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void sendChatMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("chat");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.focusstar)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.focusstar))
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);

    }

    public void sendSubscribeMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.focusstar)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.focusstar))
                .setAutoCancel(true)
                .setNumber(2)  //传入未读消息的数量
                .build();
        manager.notify(2, notification);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);//允许这个渠道下的通知显示角标
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

}
