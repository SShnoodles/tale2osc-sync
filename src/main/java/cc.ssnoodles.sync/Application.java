package cc.ssnoodles.sync;

import cn.hutool.core.lang.Singleton;
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
        log.info("start sync ......");
        OscBlogService oscBlogService = Singleton.get(OscBlogService.class);
        boolean login = oscBlogService.login();
        if (login) {
            TaleBlogService taleBlogService = Singleton.get(TaleBlogService.class);
            Contents contents = taleBlogService.getLast();
            //TODO
        }

    }
}
