package org.dromara.financinglink.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 农业知识对象 fl_agr_knowledge
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Data
@TableName("fl_agr_knowledge")
public class FlAgrKnowledge {

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
     * 首页图片
     */
    private String homePicture;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
