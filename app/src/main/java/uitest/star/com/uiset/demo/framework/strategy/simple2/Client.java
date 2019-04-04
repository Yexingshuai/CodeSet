package uitest.star.com.uiset.demo.framework.strategy.simple2;

/**
 * Created by yexing on 2019/3/14.
 */

public class Client {

    public static void main(String[] args) {

        IFinance finance = new RenZhongFinance();

        FinanceContext financeContext = new FinanceContext(finance);

//        float money = finance.finance(12, 10000);
        float money = financeContext.finance(12, 10000);

        System.out.println(money);

    }
}
