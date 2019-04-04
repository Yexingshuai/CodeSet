package uitest.star.com.uiset.demo.framework.strategy.simple2;

/**
 * Created by yexing on 2019/3/14.
 */

public class RenZhongFinance implements IFinance {
    @Override
    public float finance(int month, int money) {
        if (month == 3) {
            return money + money * 0.09f / 12 * month;
        }

        if (month == 6) {
            return money + money * 0.112f / 12 * month;
        }

        if (month == 12) {
            return money + money * 0.12f / 12 * month;
        }

        throw new IllegalArgumentException("月份不对");
    }
}
