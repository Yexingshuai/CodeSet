package uitest.star.com.uiset.entity;

/**
 * Created by yexing on 2018/12/13.
 */

public class Person {

    public String name;

    public String age;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
