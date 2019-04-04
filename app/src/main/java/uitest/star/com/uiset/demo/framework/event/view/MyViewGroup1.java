package uitest.star.com.uiset.demo.framework.event.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by yexing on 2019/1/18.
 */

public class MyViewGroup1 extends RelativeLayout {
    public static final String TAG = "MyViewGroup1";

    public MyViewGroup1(Context context) {
        super(context);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent----" + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent----" + super.onInterceptTouchEvent(ev));
//        Log.e(TAG, "onInterceptTouchEvent----" + "true");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG, "onTouEvent----" + super.onTouchEvent(event));
        Log.e(TAG, "onTouEvent----" + true);
//        return super.onTouchEvent(event);
        return true;
    }
}
