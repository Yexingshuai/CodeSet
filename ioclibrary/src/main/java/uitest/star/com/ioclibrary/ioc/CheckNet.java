package uitest.star.com.ioclibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexing on 2019/3/1.
 * 检测网络
 */

@Target(ElementType.METHOD) //代表注解的位置
@Retention(RetentionPolicy.RUNTIME)//
public @interface CheckNet {

}
