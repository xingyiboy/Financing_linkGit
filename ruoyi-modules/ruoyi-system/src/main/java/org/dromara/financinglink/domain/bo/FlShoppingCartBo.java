package org.dromara.financinglink.domain.bo;

import org.dromara.financinglink.domain.FlShoppingCart;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * 购物车业务对象 fl_shopping_cart
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@AutoMapper(target = FlShoppingCart.class, reverseConvertGenerate = false)
public class FlShoppingCartBo {

    /**
     * 编号
     */
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
