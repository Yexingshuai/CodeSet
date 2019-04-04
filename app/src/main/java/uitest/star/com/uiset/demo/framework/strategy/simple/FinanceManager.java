package uitest.star.com.uiset.demo.framework.strategy.simple;

/**
 * Created by yexing on 2019/3/14.
 */

public class FinanceManager {

    public enum Finance {

        ZHIFU_BAO, REN_ZHONG   //还会有很多,会导致这个类越来越庞大,每种利息也是不一样的
    }

    public float zhufubaoFiance(int month, int money) {

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


    public float renZhongFiance(int month, int money) {

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

    public float finance(int month, int money, Finance finance) {

        switch (finance) {
            case ZHIFU_BAO:
                break;
            case REN_ZHONG:
                break;
            default:
                return 0f;
        }
        return 0f;
    }
}
