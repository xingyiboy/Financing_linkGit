package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

import java.io.Serial;

/**
 * 规格库存对象 fl_specification
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_specification")
public class FlSpecification  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 商品编号
     */
    private Long goodsId;

    /**
     * 对应的单位
     */
    private Integer unit;

    /**
     * 单价
     */
    private BigDecimal univalence;

    /**
     * 展示图片
     */
    private String homePicture;

    /**
     * 说明
     */
    private String content;

    /**
     * 库存
     */
    private Long inventory;


}
