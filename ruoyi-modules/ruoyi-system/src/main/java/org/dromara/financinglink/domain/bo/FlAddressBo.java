package org.dromara.financinglink.domain.bo;

import org.dromara.financinglink.domain.FlAddress;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户地址业务对象 fl_address
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@AutoMapper(target = FlAddress.class, reverseConvertGenerate = false)
public class FlAddressBo  {

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 地址编码
     */
    private String addressCode;

    /**
     * 详细地址
     */
    private String addressDetail;

    /**
     * 是否是默认地址 0不是 1是
     */
    private Long isDefault;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收货人手机号
     */
    private String phone;


}
