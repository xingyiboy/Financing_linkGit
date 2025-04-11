package org.dromara.financinglink.domain.vo;

import org.dromara.financinglink.domain.FlVarietys;
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
 * 品种树视图对象 fl_varietys
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlVarietys.class)
public class FlVarietysVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 父id
     */
    @ExcelProperty(value = "父id")
    private Long parentId;

    /**
     * 值
     */
    @ExcelProperty(value = "值")
    private String label;


}
