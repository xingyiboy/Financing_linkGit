package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 农业知识点赞对象 fl_agr_know_like
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_agr_know_like")
public class FlAgrKnowLike{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 农业知识编号
     */
    private Long agrKnowId;


}
