package org.dromara.financinglink.domain.bo;

import org.dromara.financinglink.domain.FlAgrKnowLike;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 农业知识点赞业务对象 fl_agr_know_like
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@AutoMapper(target = FlAgrKnowLike.class, reverseConvertGenerate = false)
public class FlAgrKnowLikeBo {

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 农业知识编号
     */
    private Long agrKnowId;


}
