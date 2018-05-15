package cc.ssnoodles.sync;

import lombok.Getter;
import lombok.Setter;

/**
 * 博客文章字
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/15 00:08
 */
@Setter
@Getter
public class Content {
    private String draft;
    private String user_code;
    private String catalog;
    private String title;
    private String content_type;
    private String tags;
    private String classification;
    private String type;
    private String privacy;
    private String deny_comment;
    private String as_top;
    private String isRecommend;
    private String content;
    private String temp;
}
