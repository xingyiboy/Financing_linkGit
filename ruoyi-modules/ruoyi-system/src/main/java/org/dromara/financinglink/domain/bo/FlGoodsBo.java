package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.financinglink.domain.FlGoods;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlSpecification;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品和需求业务对象 fl_goods
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Data
@AutoMapper(target = FlGoods.class, reverseConvertGenerate = false)
public class FlGoodsBo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 详细内容
     */
    private String content;

    /**
     * 首页图片
     */
    private String homePicture;

    /**
     * 一级品种
     */
    private Long varietyFirst;

    /**
     * 二级品种
     */
    private Long varietySecond;

    /**
     * 三级品种
     */
        private Long varietyThird;

    /**
     * 图片
     */
    private String picture;

    /**
     * 单价
     */
    private BigDecimal univalence;

    /**
     * 单价对应的单位
     */
    private Integer unit;

    /**
     * 起售价(元)
     */
    private Long minimumPrice;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 类型 0需求 1销售
     */
    private Integer type;

    /**
     * 发货详细地址
     */
    private String addressDetail;

    /**
     * 地址编码
     */
    private String addressCode;

    /**
     * 物流方式
     */
    private String logistics;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 销量
     */
    private Integer salesVolume;

    /**
     * 排序类型
     */
    private Integer orderByType;

    /**
     * 规格列表
     */
    private List<FlSpecification> specificationList;

}
