package org.dromara.financinglink.domain.bo;

import org.dromara.financinglink.domain.FlVarietys;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 品种树业务对象 fl_varietys
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Data
@AutoMapper(target = FlVarietys.class, reverseConvertGenerate = false)
public class FlVarietysBo {

    /**
     * 编号
     */
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
