package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.financinglink.domain.FlSpecialist;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

/**
 * 专家详情业务对象 fl_specialist
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@AutoMapper(target = FlSpecialist.class, reverseConvertGenerate = false)
public class FlSpecialistBo  {
    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

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
