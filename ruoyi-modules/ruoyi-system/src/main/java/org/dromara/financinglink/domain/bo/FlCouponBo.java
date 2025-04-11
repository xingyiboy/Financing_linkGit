package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.financinglink.domain.FlCoupon;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠卷业务对象 fl_coupon
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@AutoMapper(target = FlCoupon.class, reverseConvertGenerate = false)
public class FlCouponBo {

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
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;


}
