package uitest.star.com.uiset.entity;

import java.io.Serializable;

/**
 * Created by yexing on 2019/1/7.
 */

public class Tab implements Serializable {


    public String name;
    public Class clz;

    public Tab(String name, Class clz) {
        this.name = name;
        this.clz = clz;
    }
}
