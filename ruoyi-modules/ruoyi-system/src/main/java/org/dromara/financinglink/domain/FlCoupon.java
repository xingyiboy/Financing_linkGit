package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 优惠卷对象 fl_coupon
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_coupon")
public class FlCoupon  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 起减价
     */
    private BigDecimal startMoney;

    /**
     * 减少价格
     */
    private BigDecimal discountMoney;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
