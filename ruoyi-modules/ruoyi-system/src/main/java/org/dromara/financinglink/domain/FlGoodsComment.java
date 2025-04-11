package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 商品评价对象 fl_goods_comment
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_goods_comment")
public class FlGoodsComment  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 商品编号
     */
    private Long goodsId;

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
