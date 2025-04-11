package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 购物车对象 fl_shopping_cart
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@TableName("fl_shopping_cart")
public class FlShoppingCart {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 规格库存编号
     */
    private Long specificationId;

    /**
     * 购买数量
     */
    private Long number;
    /**
     * 更新时间
     */
    private Date updateTime;


}
