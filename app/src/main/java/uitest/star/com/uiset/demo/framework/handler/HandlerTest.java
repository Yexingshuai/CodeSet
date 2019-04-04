package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class HandlerTest {


    public static void main(String[] s) {


        Looper.prepare();
        ActivityThread thread = new ActivityThread();
        thread.attach(false);

        Looper.loop();

    }
}
