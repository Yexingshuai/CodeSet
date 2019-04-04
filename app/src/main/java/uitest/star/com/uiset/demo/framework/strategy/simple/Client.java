package uitest.star.com.uiset.demo.framework.strategy.simple;

/**
 * Created by yexing on 2019/3/14.
 */

public class Client {

    public static void main(String[] args) {
        FinanceManager financeManager = new FinanceManager();

        float money = financeManager.finance(3, 10000, FinanceManager.Finance.ZHIFU_BAO);

        System.out.println(money);

    }
}
