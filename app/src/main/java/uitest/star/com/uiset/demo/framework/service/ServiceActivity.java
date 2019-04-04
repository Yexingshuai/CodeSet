package uitest.star.com.uiset.demo.framework.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import uitest.star.com.uiset.R;
import uitest.star.com.uiset.base.UIActivity;

public class ServiceActivity extends UIActivity implements View.OnClickListener {


    private Myconn myconn;
    private IService mBinder;
    private boolean isBind;

    @Override
    protected int inflateLayout() {
        return R.layout.activity_service;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void initView() {
        Button mStart = findViewById(R.id.bt_start_service);
        mStart.setOnClickListener(this);
        Button mStop = findViewById(R.id.bt_stop_service);
        mStop.setOnClickListener(this);
        Button mBindStart = findViewById(R.id.bt_bind_service);
        mBindStart.setOnClickListener(this);
        Button mBindStop = findViewById(R.id.bt_bind_stop_service);
        mBindStop.setOnClickListener(this);
        Button mBtToast = findViewById(R.id.bt_toast);
        mBtToast.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        myconn = new Myconn();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_service:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.bt_stop_service:
                Intent intent2 = new Intent(this, MyService.class);
                stopService(intent2);// 关闭服务
                break;
            case R.id.bt_bind_service:
                isBind = true;
                Intent intent3 = new Intent(this, MyService.class);
                bindService(intent3, myconn, BIND_AUTO_CREATE);  //参数3  标记自动创建 远程服务 不需要开始 就会自动开启
                break;
            case R.id.bt_bind_stop_service:
                if (isBind) {
                    unbindService(myconn);
                }
                isBind = false;
                break;

            case R.id.bt_toast:
                if (mBinder != null) {
                    mBinder.showToast();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            unbindService(myconn);
        }
    }

    class Myconn implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务连接的时候执行
            Log.e("Tag", "服务连接了");
            mBinder = (IService) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开连接的时候  失败的时候
            Log.e("Tag", "服务断掉了");
        }
    }

    class Myconn1 implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务连接的时候执行
            Log.e("Tag", "服务连接了");
//            IService1 iService1 = IService1.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开连接的时候  失败的时候
            Log.e("Tag", "服务断掉了");
        }
    }

}
