package org.dromara.financinglink.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.dromara.financinglink.domain.FlQuestionsAnswers;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * 问答业务对象 fl_questions_answers
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@AutoMapper(target = FlQuestionsAnswers.class, reverseConvertGenerate = false)
public class FlQuestionsAnswersBo  {
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
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 专家编号
     */
    private Long specialist;

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
