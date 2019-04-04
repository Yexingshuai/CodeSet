package uitest.star.com.uiset.demo.framework.strategy.simple2;

/**
 * Created by yexing on 2019/3/14.
 */

public class ZhifubaoFinance implements IFinance {
    @Override
    public float finance(int month, int money) {
        if (month == 3) {
            return money + money * 0.047f / 12 * month;
        }

        if (month == 6) {
            return money + money * 0.05f / 12 * month;
        }

        if (month == 12) {
            return money + money * 0.06f / 12 * month;
        }
        throw new IllegalArgumentException("月份不对");
    }
}
