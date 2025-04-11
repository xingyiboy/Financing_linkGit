package org.dromara.financinglink.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlAgrKnowComment;
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
 * 农业知识评价视图对象 fl_agr_know_comment
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlAgrKnowComment.class)
public class FlAgrKnowCommentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号")
    private Long userId;

    /**
     * 农业知识编号
     */
    @ExcelProperty(value = "农业知识编号")
    private Long agrKnowId;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String content;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 用户昵称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "userId")
    private String userIdNickname;


}
