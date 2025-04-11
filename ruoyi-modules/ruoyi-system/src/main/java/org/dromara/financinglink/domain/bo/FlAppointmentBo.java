package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.financinglink.domain.FlAppointment;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * 预约业务对象 fl_appointment
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@AutoMapper(target = FlAppointment.class, reverseConvertGenerate = false)
public class FlAppointmentBo  {
    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 种植作物
     */
    private String crop;

    /**
     * 种植作物详细信息
     */
    private String cropDetail;

    /**
     * 作物条件
     */
    private String cropCondition;

    /**
     * 土壤条件
     */
    private String edaphicCondition;

    /**
     * 面积
     */
    private String area;

    /**
     * 电话
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;
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
     * 专家编号
     */
    private Long specialist;


}
