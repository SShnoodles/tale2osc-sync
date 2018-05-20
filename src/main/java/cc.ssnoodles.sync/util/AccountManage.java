package cc.ssnoodles.sync.util;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/17 22:05
 */

public class AccountManage {
    private AccountManage() {
    }

    /**
     * 账户cookie
     */
    private static final ThreadLocal<String> ACCOUNT = new ThreadLocal<>();
    /**
     * 账户登录次数
     */
    private static final ThreadLocal<Integer> ACCOUNT_LOGIN_TIMES = ThreadLocal.withInitial(() -> 0);

    public static ThreadLocal<String> getAccount() {
        return ACCOUNT;
    }
    public static ThreadLocal<Integer> getAccountLoginTimes() {
        return ACCOUNT_LOGIN_TIMES;
    }

}
