package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class Handler {
    MessageQueue mQueue;


    public Handler() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            throw new RuntimeException("不能在Looper.prepare()之前 创建handler");
        }

        mQueue = looper.mQueue;
    }

    public void sendMessage(Message message) {
        sendMessageDelayed(message, 0);
    }


    public final boolean sendMessageDelayed(Message message, long delayMillils) {
        if (delayMillils < 0) {
            delayMillils = 0;
        }

        return sendMessageAtTime(message, System.currentTimeMillis() + delayMillils);

    }

    private boolean sendMessageAtTime(Message message, long upTimeMills) {
        MessageQueue queue = mQueue;
        return enqueueMessage(queue, message, upTimeMills);

    }

    private boolean enqueueMessage(MessageQueue queue, Message message, long upTimeMills) {

        message.target = this;
        return queue.enqueueMessage(message, upTimeMills);
    }

    public void handleMessage(Message msg) {

    }
}
