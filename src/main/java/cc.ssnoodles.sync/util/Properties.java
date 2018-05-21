package cc.ssnoodles.sync.util;

import cn.hutool.setting.dialect.Props;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/19 23:49
 */
public class Properties {
    public static final Props PROPS = new Props("app.properties");
    public static final String OSC_EMAIL = "osc.email";
    public static final String OSC_PWD = "osc.pwd";
    public static final String OSC_USER_CODE = "osc.user_code";
    public static final String DB_PATH = "db.path";
    public static final String OSC_LOGIN_TIMES = "osc.logintimes";
    public static final String OSC_LOGIN_TIMES_INTERVAL = "osc.logintimes.interval";
    public static final String OSC_CONTENT_CATALOG = "osc.content.catalog";
    public static final String OSC_CONTENT_CLASSIFICATION = "osc.content.classification";
}
