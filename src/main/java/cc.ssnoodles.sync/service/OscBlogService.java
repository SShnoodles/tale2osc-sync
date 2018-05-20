package cc.ssnoodles.sync.service;

import cc.ssnoodles.sync.entity.Content;
import cc.ssnoodles.sync.util.AccountManage;
import cc.ssnoodles.sync.util.Properties;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;

import java.util.HashMap;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/14 23:02
 */
public class OscBlogService {
    private static Props PROPS = Properties.PROPS;
    private static final Log LOG = LogFactory.get();
    private static final String LOGIN_URL = "https://www.oschina.net/action/user/hash_login?from=";
    private static final String SAVE_URL = "https://my.oschina.net/action/blog/save";
    private static final String EDIT_URL = "https://my.oschina.net/action/blog/edit";
    private static final String OSCID = "oscid";
    private static final String ERROR = "error";
    private static ThreadLocal<String> accountThreadLocal = AccountManage.getAccount();
    /**
     * 登陆
     */
    public boolean login() {
        String email = PROPS.getStr(Properties.OSC_EMAIL);
        String pwd = PROPS.getStr(Properties.OSC_PWD);

        HashMap<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("email", email);
        paramMap.put("pwd", pwd);
//        HttpResponse response = HttpRequest.post(LOGIN_URL)
//                .form(paramMap)
//                .execute();
//        List<HttpCookie> httpCookies = response.getCookie();
//        if (HttpStatus.HTTP_OK == response.getStatus()
//                && CollUtil.isNotEmpty(httpCookies)
//                && httpCookies.get(0).getName().equals(OSCID)){
//            String cookie = httpCookies.get(0).toString();
//            accountThreadLocal.set(cookie);
//            LOG.debug("login success, cookie : {}", cookie);
//            return true;
//        }
        LOG.debug("login failed ! this email : {}", email);
        return false;
    }

    /**
     * 保存博文
     * @param cookie
     * @param content
     * @return
     */
    public boolean saveBlog(String cookie, Content content) {
        content.setUser_code(PROPS.getStr(Properties.OSC_USER_CODE));
        HttpResponse response = HttpRequest.post(SAVE_URL)
                .header(Header.COOKIE, cookie)
                .form(BeanUtil.beanToMap(content))
                .execute();
        return findError(response);
    }

    private boolean findError(HttpResponse response) {
        if (HttpStatus.HTTP_OK == response.getStatus()) {
            String body = response.body();
            if (StrUtil.isNotBlank(body) && body.contains(ERROR)){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 编辑博文
     * @param cookie
     * @param content
     * @return
     */
    public boolean editBlog(String cookie, Content content) {
        content.setUser_code(PROPS.getStr(Properties.OSC_USER_CODE));
        HttpResponse response = HttpRequest.post(EDIT_URL)
                .header(Header.COOKIE, cookie)
                .form(BeanUtil.beanToMap(content))
                .execute();
        return findError(response);
    }
}
