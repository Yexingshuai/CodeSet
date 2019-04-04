package uitest.star.com.uiset.demo.view.toolbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yexing on 2019/1/22.
 */

public abstract class AbsNavigation implements INavigation {

    private Builder.Parms P;
    private View navigationView;


    public AbsNavigation(Builder.Parms P) {
        this.P = P;
        createAndBindView();
    }


    protected Builder.Parms getParams() {
        return P;
    }

    @Override
    public void createAndBindView() {
        //1.创建和绑定View
        navigationView = View.inflate(P.context, bindLayoutId(), null);

        //2.将创建的navigationView加载到 ViewGroup  parent里面去
        P.parent.addView(navigationView, 0); //加到最上面

        //3.绑定参数
        applyView();
    }


    public View findViewById(int viewId) {

        return navigationView.findViewById(viewId);
    }

    public abstract static class Builder<P extends Builder.Parms> {

        //这个方法必须要由子类实现
        public abstract AbsNavigation create();


        public static class Parms {
            public Context context;
            public ViewGroup parent;//头部添加地方，父布局


            public Parms(Context context, ViewGroup parent) {
                this.context = context;
                this.parent = parent;
            }
        }
    }
}
