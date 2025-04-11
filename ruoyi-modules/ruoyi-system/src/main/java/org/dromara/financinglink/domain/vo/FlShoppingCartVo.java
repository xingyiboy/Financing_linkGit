package org.dromara.financinglink.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlGoods;
import org.dromara.financinglink.domain.FlShoppingCart;
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
 * 购物车视图对象 fl_shopping_cart
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlShoppingCart.class)
public class FlShoppingCartVo implements Serializable {

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
    @ExcelProperty(value = "用户编号")
    private Long userId;

    /**
     * 规格库存编号
     */
    @ExcelProperty(value = "规格库存编号")
    private Long specificationId;
    /**
     * 对应规格数据
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "specificationId")
    private String specificationVo;

    /**
     * 购买数量
     */
    @ExcelProperty(value = "购买数量")
    private Long number;
    /**
     * 对应商品数据
     */
    private FlGoods goodsVo;
    /**
     * 更新时间
     */
    private Date updateTime;
}
