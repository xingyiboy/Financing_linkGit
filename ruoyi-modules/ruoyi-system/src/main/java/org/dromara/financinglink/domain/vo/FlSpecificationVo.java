package org.dromara.financinglink.domain.vo;

import java.math.BigDecimal;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlSpecification;
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
 * 规格库存视图对象 fl_specification
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlSpecification.class)
public class FlSpecificationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 商品编号
     */
    @ExcelProperty(value = "商品编号")
    private Long goodsId;

    /**
     * 对应的单位
     */
    @ExcelProperty(value = "对应的单位", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "fl_goods_unit")
    private Integer unit;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    private BigDecimal univalence;

    /**
     * 展示图片
     */
    @ExcelProperty(value = "展示图片")
    private String homePicture;

    /**
     * 展示图片Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "homePicture")
    private String homePictureUrl;
    /**
     * 说明
     */
    @ExcelProperty(value = "说明")
    private String content;

    /**
     * 库存
     */
    @ExcelProperty(value = "库存")
    private Long inventory;


}
