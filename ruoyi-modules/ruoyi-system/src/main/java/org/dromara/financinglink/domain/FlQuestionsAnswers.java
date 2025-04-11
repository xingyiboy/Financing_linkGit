package org.dromara.financinglink.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 问答对象 fl_questions_answers
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@TableName("fl_questions_answers")
public class FlQuestionsAnswers  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Long status;

    /**
     * 农作物名称
     */
    private String flName;

    /**
     * 回答
     */
    private String answer;

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
    /**
     * 联系人手机号
     */
    private String phone;


}
