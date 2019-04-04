package uitest.star.com.ioclibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by yexing on 2019/3/1.
 */

public class ViewUtils {


    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }


    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面的方法     object-->反射需要执行的类
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);

        injectEvent(finder, object);
    }

    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面的所有方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();


        //2.获取onClick里面的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById 找到view
                    View view = finder.findViewById(viewId);
                    //扩展网络

                    CheckNet annotation = method.getAnnotation(CheckNet.class);
                    boolean isCheckNet = false;
                    if (annotation != null) {
                        isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    }

                    if (view != null) {
                        //4.view.setOnClickListener
                        view.setOnClickListener(new DeClaredOnClickListener(method, object, isCheckNet));
                    }
                }
            }
        }


    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {

        //1.获取类里面的所有属性
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();//获取所有属性，包括共有私有

        //2.获取ViewById里面的value值
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的id  -->R.id.tv
                int viewId = viewById.value();
                //3.findViewById 找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //越过语法检查
                    field.setAccessible(true);
                    //4.动态的注入找到的view
                    try {
                        field.set(object, view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DeClaredOnClickListener implements View.OnClickListener {

        private Object mObject;
        private boolean mIsCheckNet;
        private Method mMethod;


        public DeClaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View view) {
            //判断是否需要检测网络
            if (mIsCheckNet) {
                if (!isNetworkAvailable(view.getContext())) {
                    Toast.makeText(view.getContext(), "亲，您的网络不给力呦！", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            try {
                //5.反射执行方法
                mMethod.setAccessible(true);
                mMethod.invoke(mObject, view);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }


    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }

}
