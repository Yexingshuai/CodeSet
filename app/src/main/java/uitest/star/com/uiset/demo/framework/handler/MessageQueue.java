package uitest.star.com.uiset.demo.framework.handler;

/**
 * Created by yexing on 2019/2/14.
 */

public class MessageQueue {

    private Message mMessages;

    public boolean enqueueMessage(Message msg, long when) {
        synchronized (this) {
            msg.when = when;
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {

                msg.next = p;
                mMessages = msg;
            } else {

                Message preV;
                for (; ; ) {
                    preV = p;
                    p = p.next;

                    if (p == null || when < p.when) {
                        break;
                    }

                }
                msg.next = p;
                preV.next = msg;

            }
        }
        return true;
    }

    public Message next() {
        return null;
    }
}
