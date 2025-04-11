package org.dromara.financinglink.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlAppointment;
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
 * 预约视图对象 fl_appointment
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlAppointment.class)
public class FlAppointmentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    private String address;

    /**
     * 种植作物
     */
    @ExcelProperty(value = "种植作物")
    private String crop;

    /**
     * 种植作物详细信息
     */
    @ExcelProperty(value = "种植作物详细信息")
    private String cropDetail;

    /**
     * 作物条件
     */
    @ExcelProperty(value = "作物条件")
    private String cropCondition;

    /**
     * 土壤条件
     */
    @ExcelProperty(value = "土壤条件")
    private String edaphicCondition;

    /**
     * 面积
     */
    @ExcelProperty(value = "面积")
    private String area;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String phone;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建者昵称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "createBy")
    private String createByNickname;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
