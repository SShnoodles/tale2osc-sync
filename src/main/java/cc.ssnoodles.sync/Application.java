package cc.ssnoodles.sync;

import cn.hutool.cron.CronUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;


/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/14 22:40
 */
public class Application {
    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        log.info("Startup sync ......");
        CronUtil.start();
        ConfigMonitor.start();
    }
}
