package cc.ssnoodles.sync;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/17 22:05
 */

public class AccountManage {
    private AccountManage() {
    }
    private static final ThreadLocal<String> ACCOUNT_THREAD_LOCAL = new ThreadLocal<>();

    public static ThreadLocal<String> getAccountThreadLocal() {
        return ACCOUNT_THREAD_LOCAL;
    }
}
