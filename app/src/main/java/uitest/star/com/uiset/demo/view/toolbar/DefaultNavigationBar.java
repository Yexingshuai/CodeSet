package uitest.star.com.uiset.demo.view.toolbar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by yexing on 2019/1/22.
 */

public class DefaultNavigationBar extends AbsNavigation {


    public DefaultNavigationBar(Builder.Parms P) {
        super(P);
    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {
        //绑定头部文字

    }

    protected static class Builder extends AbsNavigation.Builder {


        private DefaultParams P;

        public Builder(Context context, ViewGroup parent) {
            P = new DefaultParams(context, parent);
        }

        @Override
        public AbsNavigation create() {
            return new DefaultNavigationBar(P);
        }

        public void setTitle(String title) {
            P.title = title;
        }


        public static class DefaultParams extends Parms {
            public String title;


            public DefaultParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }
}
