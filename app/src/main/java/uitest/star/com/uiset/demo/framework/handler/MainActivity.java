package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class MainActivity extends Activity {

    private TextView mTextView;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            mTextView.setText((CharSequence) msg.obj);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mTextView = new TextView();
        System.out.println("---------onCreate--");


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.obj = "Hello,大家好";
                mHandler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
