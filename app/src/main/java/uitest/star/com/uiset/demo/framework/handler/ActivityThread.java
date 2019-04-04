package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class ActivityThread {

    final H mH = new H();


    public void attach(boolean b) {


        Activity mainActivity = new MainActivity();
        mainActivity.onCreate();


        //通过Handler 执行 Activity 生命周期

        Message message = new Message();
        message.obj = mainActivity;
        mH.sendMessage(message);
    }


    private class H extends Handler {


        public void handleMessage(Message msg) {
            Activity mainActivity = (Activity) msg.obj;
            mainActivity.onResume();
        }

        public void sendMessage(Message message) {

        }
    }

}
