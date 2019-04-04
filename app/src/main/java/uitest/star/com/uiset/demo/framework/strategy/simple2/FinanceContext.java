package uitest.star.com.uiset.demo.framework.strategy.simple2;

/**
 * Created by yexing on 2019/3/14.
 * 策略模式的上下文，相当于android里面的context
 * 可以获取一些额外的基本信息
 */

public class FinanceContext {
    IFinance finance;

    public FinanceContext(IFinance finance) {
        this.finance = finance;
    }

    public float finance(int month, int money) {
        return finance.finance(month, money);
    }

}
