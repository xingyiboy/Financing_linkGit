package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.financinglink.domain.FlGoodsComment;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * 商品评价业务对象 fl_goods_comment
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@AutoMapper(target = FlGoodsComment.class, reverseConvertGenerate = false)
public class FlGoodsCommentBo  {

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 商品编号
     */
    private Long goodsId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 商品订单id
     */
    private Long orderId;


}
