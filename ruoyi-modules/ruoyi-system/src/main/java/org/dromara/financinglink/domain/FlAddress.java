package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户地址对象 fl_address
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Data
@TableName("fl_address")
public class FlAddress  {

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
