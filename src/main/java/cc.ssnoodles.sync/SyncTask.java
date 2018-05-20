package cc.ssnoodles.sync;

import cc.ssnoodles.sync.entity.Content;
import cc.ssnoodles.sync.entity.Contents;
import cc.ssnoodles.sync.entity.ContentsRecord;
import cc.ssnoodles.sync.util.AccountManage;
import cc.ssnoodles.sync.service.OscBlogService;
import cc.ssnoodles.sync.service.TaleBlogService;
import cc.ssnoodles.sync.util.Properties;
import cn.hutool.core.lang.Singleton;
import cn.hutool.cron.CronUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import java.time.Instant;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/19 23:25
 */
public class SyncTask implements Runnable{
    private static final Log log = LogFactory.get();

    @Override
    public void run() {
        sync();
    }

    public void sync() {
        log.info("Start the sync task ...");
        Props props = Properties.PROPS;
        TaleBlogService taleBlogService = Singleton.get(TaleBlogService.class);
        OscBlogService oscBlogService = Singleton.get(OscBlogService.class);

        Contents newContents = taleBlogService.getLast();
        // 有记录就跳过
        ContentsRecord newContentsRecord = taleBlogService.getByTaleContentId(newContents.getCid());
        if (newContentsRecord != null) {
            log.info("no blog content to sync ...");
            return;
        }
        syncContent(props, oscBlogService, newContents, false);
    }

    private void syncContent(Props props, OscBlogService oscBlogService, Contents newContents, boolean login) {
        ThreadLocal<String> account = AccountManage.getAccount();
        ThreadLocal<Integer> loginTimes = AccountManage.getAccountLoginTimes();
        // 只需登陆一次
        if (account.get() == null) {
            // 登录次数超过3次就停止任务
            if (checkLoginTimes(newContents.getTitle(), loginTimes, props)) {
                return;
            }
            try {
                Thread.sleep(props.getInt(Properties.OSC_LOGIN_TIMES_INTERVAL));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            login = oscBlogService.login();
            loginTimes.set(loginTimes.get() + 1);
            log.info("login osc {} times ", loginTimes.get());
        }
        if (login) {
            Content content = new Content();
            content.setUser_code(props.getStr("osc.user_code"));
            content.setTitle(newContents.getTitle());
            content.setTags(newContents.getTags());
            content.setContent(newContents.getContent());
            boolean save = oscBlogService.saveBlog(account.get(), content);
            if (save) {
                log.info("save success, blog content title [{}]", newContents.getTitle());
                ContentsRecord contentsRecord = new ContentsRecord();
                contentsRecord.setTaleContentId(newContents.getCid());
                contentsRecord.setSyncTime((int)Instant.now().getEpochSecond());
                contentsRecord.setSyncStatus(1);
                contentsRecord.save();
            }else {
                log.error("save failure！, blog content title [{}]", newContents.getTitle());
                loginAgain(props, oscBlogService, newContents, loginTimes, account);
            }
        }else {
            loginAgain(props, oscBlogService, newContents, loginTimes, account);
        }
    }

    /**
     * 重新登录
     */
    private void loginAgain(Props props, OscBlogService oscBlogService, Contents newContents,
                            ThreadLocal<Integer> loginTimes, ThreadLocal<String> account) {
        // 登录次数超过3次就停止任务
        if (checkLoginTimes(newContents.getTitle(), loginTimes, props)) {
            return;
        }
        try {
            Thread.sleep(props.getInt(Properties.OSC_LOGIN_TIMES_INTERVAL));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.remove();
        boolean loginAgain = oscBlogService.login();
        loginTimes.set(loginTimes.get() + 1);
        log.info("login osc {} times", loginTimes.get());
        syncContent(props, oscBlogService, newContents, loginAgain);
    }

    private boolean checkLoginTimes(String title, ThreadLocal<Integer> loginTimes, Props props) {
        if (loginTimes.get() >= props.getInt(Properties.OSC_LOGIN_TIMES)) {
            log.error("login times >= 3 task stop！");
            loginTimes.remove();
            CronUtil.stop();
            return true;
        }
        return false;
    }
}
