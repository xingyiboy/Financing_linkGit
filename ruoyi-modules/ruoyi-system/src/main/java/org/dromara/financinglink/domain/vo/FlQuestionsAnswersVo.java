package org.dromara.financinglink.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlQuestionsAnswers;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 问答视图对象 fl_questions_answers
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlQuestionsAnswers.class)
public class FlQuestionsAnswersVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "fl_questions_status")
    private Long status;

    /**
     * 农作物名称
     */
    @ExcelProperty(value = "农作物名称")
    private String flName;
    /**
     * 专家编号
     */
    private Long specialist;

    /**
     * 回答
     */
    @ExcelProperty(value = "回答")
    private String answer;
    /**
     * 联系人手机号
     */
    private String phone;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;

    /**
     * 专家昵称
     */
    private String specialistNickname;

    /**
     * 创建者昵称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "createBy")
    private String createByNickname;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
