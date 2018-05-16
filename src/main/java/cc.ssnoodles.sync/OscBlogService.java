package cc.ssnoodles.sync;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
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
    private static final String LOGIN_URL = "https://www.oschina.net/action/user/hash_login?from=";
    private static final String SAVE_URL = "https://my.oschina.net/action/blog/save";
    private static final String EDIT_URL = "https://my.oschina.net/action/blog/edit";
    /**
     * 登陆
     */
    public String login() {
        String email = PROPS.getStr("osc.email");
        String pwd = PROPS.getStr("osc.pwd");

        HashMap<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("email", email);
        paramMap.put("pwd", pwd);
        HttpResponse response = HttpRequest.post(LOGIN_URL)
                .form(paramMap)
                .execute();
        List<HttpCookie> httpCookies = response.getCookie();
        if (HttpStatus.HTTP_OK == response.getStatus() && CollUtil.isNotEmpty(httpCookies)){
            String cookie = httpCookies.get(0).toString();
            LOG.debug("login success, cookie : {}", cookie);
            return cookie;
        }
        LOG.error("login failed ! this email : {}", email);
        return null;
    }

    /**
     * 保存博客
     * @param cookie
     * @param content
     * @return
     */
    public int saveBlog(String cookie, Content content) {
        return HttpRequest.post(SAVE_URL)
                .header(Header.COOKIE, cookie)
                .form(BeanUtil.beanToMap(content))
                .execute().getStatus();
    }

    /**
     * 修改博客
     * @param cookie
     * @param content
     * @return
     */
    public int editBlog(String cookie, Content content) {
        return HttpRequest.post(EDIT_URL)
                .header(Header.COOKIE, cookie)
                .form(BeanUtil.beanToMap(content))
                .execute().getStatus();
    }
}
