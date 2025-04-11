package org.dromara.financinglink.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlSpecialist;
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
 * 专家详情视图对象 fl_specialist
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlSpecialist.class)
public class FlSpecialistVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号")
    private Long userId;

    /**
     * 专家姓名
     */
    @ExcelProperty(value = "专家姓名")
    private String name;

    /**
     * 职称
     */
    @ExcelProperty(value = "职称")
    private String professional;

    /**
     * 专业
     */
    @ExcelProperty(value = "专业")
    private String major;

    /**
     * 电话
     */
    @ExcelProperty(value = "电话")
    private String phone;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String address;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatar;

    /**
     * 头像Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "avatar")
    private String avatarUrl;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private Long createBy;

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
