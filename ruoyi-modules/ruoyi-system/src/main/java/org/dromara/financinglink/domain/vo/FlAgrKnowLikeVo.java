package org.dromara.financinglink.domain.vo;

import org.dromara.financinglink.domain.FlAgrKnowLike;
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
 * 农业知识点赞视图对象 fl_agr_know_like
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlAgrKnowLike.class)
public class FlAgrKnowLikeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号")
    private Long userId;

    /**
     * 农业知识编号
     */
    @ExcelProperty(value = "农业知识编号")
    private Long agrKnowId;


}
