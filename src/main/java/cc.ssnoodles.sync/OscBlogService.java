package cc.ssnoodles.sync;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/14 23:02
 */
public class OscBlogService {
    private static final Props PROPS = new Props("app.properties");
    private static final Log LOG = LogFactory.get();
    private static final String OSCID = "oscid";
    /**
     * 登陆
     */
    public String login() {
        String email = PROPS.getStr("osc.email");
        String pwd = PROPS.getStr("osc.pwd");
        String loginUrl = PROPS.getStr("osc.loginUrl");

        HashMap<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("email", email);
        paramMap.put("pwd", pwd);
        List<HttpCookie> httpCookies = HttpRequest.post(loginUrl)
                .form(paramMap)
                .execute().getCookie();
        if (CollUtil.isNotEmpty(httpCookies)) {
            return httpCookies.get(0).toString();
        }
        return null;
    }

    /**
     * 保存博客
     * @param cookie
     * @param content
     * @return
     */
    public String saveBlog(String cookie, Content content) {
        String saveBlogUrl = PROPS.getStr("osc.saveBlogUrl");

        String body = HttpRequest.post(saveBlogUrl)
                .header(Header.COOKIE, cookie)
                .form(BeanUtil.beanToMap(content))
                .execute().body();

        return null;
    }
}
