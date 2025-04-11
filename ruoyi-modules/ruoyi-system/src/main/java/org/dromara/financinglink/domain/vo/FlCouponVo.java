package org.dromara.financinglink.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.financinglink.domain.FlCoupon;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 优惠卷视图对象 fl_coupon
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlCoupon.class)
public class FlCouponVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号")
    private Long userId;

    /**
     * 起减价
     */
    @ExcelProperty(value = "起减价")
    private BigDecimal startMoney;

    /**
     * 减少价格
     */
    @ExcelProperty(value = "减少价格")
    private BigDecimal discountMoney;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    private Date endTime;


}
