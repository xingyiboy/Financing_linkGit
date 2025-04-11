package org.dromara.financinglink.domain.bo;

import org.dromara.financinglink.domain.FlSpecification;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

/**
 * 规格库存业务对象 fl_specification
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@AutoMapper(target = FlSpecification.class, reverseConvertGenerate = false)
public class FlSpecificationBo {

    /**
     * 编号
     */
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
