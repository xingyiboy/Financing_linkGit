package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

import java.io.Serial;

/**
 * 专家详情对象 fl_specialist
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@TableName("fl_specialist")
public class FlSpecialist {

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
    private Long userId;

    /**
     * 专家姓名
     */
    private String name;

    /**
     * 职称
     */
    private String professional;

    /**
     * 专业
     */
    private String major;

    /**
     * 电话
     */
    private String phone;

    /**
     * 单位
     */
    private String address;

    /**
     * 头像
     */
    private String avatar;


}
