package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 预约对象 fl_appointment
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@TableName("fl_appointment")
public class FlAppointment {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 专家编号
     */
    private Long specialist;
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


}
