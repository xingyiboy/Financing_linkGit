package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 品种树对象 fl_varietys
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Data
@TableName("fl_varietys")
public class FlVarietys{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 值
     */
    private String label;


}
