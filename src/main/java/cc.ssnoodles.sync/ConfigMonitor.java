package cc.ssnoodles.sync;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.cron.CronUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/20 11:54
 */
public class ConfigMonitor {
    private static final Log log = LogFactory.get();

    private static final String APP = "app.properties";
    private static final String TASK = "config/cron.setting";

    public static void start() {
        File file = FileUtil.file(TASK);
        //这里只监听文件或目录的修改事件
        WatchMonitor.createAll(file, new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                log.info("sync task config file was modified , restart now");
                CronUtil.restart();
            }
        }).start();
    }
}
