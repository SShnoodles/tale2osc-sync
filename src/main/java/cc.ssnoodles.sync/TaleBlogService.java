package cc.ssnoodles.sync;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import io.github.biezhi.anima.Anima;
import io.github.biezhi.anima.enums.OrderBy;

import java.util.List;

import static io.github.biezhi.anima.Anima.select;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/17 21:56
 */
public class TaleBlogService {
    private static final Props PROPS = new Props("app.properties");
    private static final Log LOG = LogFactory.get();

    static {
        // SQLite
        Anima.open("jdbc:sqlite:" + PROPS.getStr("db.address"));
    }

    /**
     * 获取所有文章
     * @return
     */
    public List<Contents> getAll() {
        return select().from(Contents.class).all();
    }

    /**
     * 获取最新的一条文章
     */
    public Contents getLast() {
        List<Contents> contents = select().from(Contents.class).order(Contents::getCreated, OrderBy.DESC).limit(1);
        return CollectionUtil.isNotEmpty(contents) ? contents.get(0) : null;
    }
}
