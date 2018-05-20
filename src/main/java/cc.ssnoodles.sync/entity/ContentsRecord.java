package cc.ssnoodles.sync.entity;

import io.github.biezhi.anima.Model;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/5/19 22:25
 */
@Data
@Table(name = "t_contents_record")
public class ContentsRecord extends Model {
    private Integer id;
    private Integer taleContentId;
    private Long oscContentId;
    /**
     * 同步时间
     */
    private Integer syncTime;
    /**
     * 0 未同步 1 已同步
     */
    private int syncStatus;
}
