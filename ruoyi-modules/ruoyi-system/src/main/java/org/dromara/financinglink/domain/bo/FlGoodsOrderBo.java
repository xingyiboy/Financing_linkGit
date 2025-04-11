package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.financinglink.domain.FlGoodsOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

/**
 * 商品订单业务对象 fl_goods_order
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@AutoMapper(target = FlGoodsOrder.class, reverseConvertGenerate = false)
public class FlGoodsOrderBo {

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 销售者编号
     */
    private Long sellId;

    /**
     * 商品编号
     */
    private Long goodsId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品首页图
     */
    private String homePicture;

    /**
     * 单价
     */
    private BigDecimal univalence;

    /**
     * 购买数量
     */
    private Long number;

    /**
     * 优惠价减少金额
     */
    private BigDecimal discountMoney;

    /**
     * 花费金额
     */
    private BigDecimal money;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 地址编号
     */
    private String addressCode;

    /**
     * 详细地址
     */
    private String addressDetails;

    /**
     * 状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 查看类型 0用户 1销售者
     */
    private Integer type;
    /**
     * 规格ID
     */
    private Long specId;
    /**
     * 是否评论
     */
    private Integer isComment;

}
