package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class TextView {

    private Thread mThread;

    public TextView() {
        this.mThread = Thread.currentThread();
    }

    public void setText(CharSequence text) {
             checkThread();

        System.out.println("更新UI成功啦！！！");
    }

    void checkThread() {
        if (mThread != Thread.currentThread()) {
            throw new RuntimeException("只有创建视图层次结构的原始线程才能触摸其视图");
        }
    }
}
