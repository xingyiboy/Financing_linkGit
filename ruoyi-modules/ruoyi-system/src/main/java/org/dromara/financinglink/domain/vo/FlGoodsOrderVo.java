package org.dromara.financinglink.domain.vo;

import java.math.BigDecimal;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlGoodsOrder;
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
 * 商品订单视图对象 fl_goods_order
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlGoodsOrder.class)
public class FlGoodsOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号")
    private Long userId;
    /**
     * 销售者编号
     */
    private Long sellId;
    /**
     * 商品编号
     */
    @ExcelProperty(value = "商品编号")
    private Long goodsId;

    /**
     * 商品标题
     */
    @ExcelProperty(value = "商品标题")
    private String title;

    /**
     * 商品首页图
     */
    @ExcelProperty(value = "商品首页图")
    private String homePicture;

    /**
     * 商品首页图Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "homePicture")
    private String homePictureUrl;
    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    private BigDecimal univalence;

    /**
     * 购买数量
     */
    @ExcelProperty(value = "购买数量")
    private Long number;

    /**
     * 优惠价减少金额
     */
    @ExcelProperty(value = "优惠价减少金额")
    private BigDecimal discountMoney;

    /**
     * 花费金额
     */
    @ExcelProperty(value = "花费金额")
    private BigDecimal money;

    /**
     * 收货人姓名
     */
    @ExcelProperty(value = "收货人姓名")
    private String name;

    /**
     * 收货人手机号
     */
    @ExcelProperty(value = "收货人手机号")
    private String phone;

    /**
     * 地址编号
     */
    @ExcelProperty(value = "地址编号")
    private String addressCode;

    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址")
    private String addressDetails;

    /**
     * 状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款
     */
    @ExcelProperty(value = "状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款")
    private Integer status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 是否评论
     */
    private Integer isComment;


}
