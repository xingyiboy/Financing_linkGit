package org.dromara.financinglink.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.financinglink.domain.FlAddress;
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
 * 用户地址视图对象 fl_address
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlAddress.class)
public class FlAddressVo implements Serializable {

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
     * 地址编码
     */
    @ExcelProperty(value = "地址编码")
    private String addressCode;

    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址")
    private String addressDetail;

    /**
     * 是否是默认地址 0不是 1是
     */
    @ExcelProperty(value = "是否是默认地址 0不是 1是", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "adress_default")
    private Long isDefault;

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


}
