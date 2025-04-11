package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 农业知识评价对象 fl_agr_know_comment
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_agr_know_comment")
public class FlAgrKnowComment  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 农业知识编号
     */
    private Long agrKnowId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
