package cc.ssnoodles.sync.entity;

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
    /**
     * 文章ID 1511470
     */
    private Long id;
    /**
     * 是否是草案 有时会是数字
     */
    private String draft = "false";
    /**
     * 用户编码
     */
    private String user_code;
    /**
     * 目录 分类 JAVA 3711389 SCALA 5694873
     */
    private int catalog = 3711389;
    /**
     * 标题
     */
    private String title;
    /**
     * 3 markdown 4 富文本
     */
    private int content_type = 3;
    /**
     * 标签 逗号分隔
     */
    private String tags;
    /**
     * 系统分类 428609 编程语言
     */
    private String classification = "428609";
    /**
     * 未知
     */
    private int type = 1;
    /**
     * 是否对所有人可见 0
     */
    private int privacy = 0;
    /**
     * 是否允许评论 0
     */
    private int deny_comment = 0;
    /**
     * 是否置顶 0
     */
    private int as_top = 0;
    /**
     * on
     */
    private String isRecommend = "on";
    /**
     * 摘要
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 时间戳
     */
    private long temp = System.currentTimeMillis();
}
