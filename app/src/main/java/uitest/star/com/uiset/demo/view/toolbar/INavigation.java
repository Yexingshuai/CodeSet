package uitest.star.com.uiset.demo.view.toolbar;

/**
 * Created by yexing on 2019/1/22.
 * 定义导航栏的规范
 */

public interface INavigation {

    //绑定 标题栏的 layout
    int bindLayoutId();

    //创建View 并添加
    void createAndBindView();

    //绑定设置参数
    void applyView();
}
