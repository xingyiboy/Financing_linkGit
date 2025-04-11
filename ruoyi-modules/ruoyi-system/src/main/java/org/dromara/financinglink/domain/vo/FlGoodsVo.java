package org.dromara.financinglink.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.financinglink.domain.FlGoods;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 商品和需求视图对象 fl_goods
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlGoods.class)
public class FlGoodsVo implements Serializable {

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
     * 详细内容
     */
    @ExcelProperty(value = "详细内容")
    private String content;

    /**
     * 首页图片
     */
    @ExcelProperty(value = "首页图片")
    private String homePicture;

    /**
     * 首页图片Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "homePicture")
    private String homePictureUrl;
    /**
     * 一级品种
     */
    @ExcelProperty(value = "一级品种")
    private Long varietyFirst;

    /**
     * 二级品种
     */
    @ExcelProperty(value = "二级品种")
    private Long varietySecond;

    /**
     * 三级品种
     */
    @ExcelProperty(value = "三级品种")
    private Long varietyThird;

    /**
     * 图片
     */
    @ExcelProperty(value = "图片")
    private String picture;

    /**
     * 图片Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "picture")
    private String pictureUrl;
    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    private BigDecimal univalence;

    /**
     * 单价对应的单位
     */
    @ExcelProperty(value = "单价对应的单位", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "fl_goods_unit")
    private Integer unit;

    /**
     * 起售价(元)
     */
    @ExcelProperty(value = "起售价(元)")
    private Long minimumPrice;

    /**
     * 状态 0禁用 1启用
     */
    @ExcelProperty(value = "状态 0禁用 1启用", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "fl_goods_status")
    private Integer status;

    /**
     * 类型 0需求 1销售
     */
    @ExcelProperty(value = "类型 0需求 1销售", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "fl_goods_type")
    private Integer type;

    /**
     * 发货详细地址
     */
    @ExcelProperty(value = "发货详细地址")
    private String addressDetail;

    /**
     * 地址编码
     */
    @ExcelProperty(value = "地址编码")
    private String addressCode;

    /**
     * 物流方式
     */
    @ExcelProperty(value = "物流方式")
    private String logistics;

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

    /**
     * 更新者
     */
    @ExcelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新者昵称
     */
    @Translation(type = TransConstant.USER_ID_TO_NICKNAME, mapper = "updateBy")
    private String updateByNickname;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 销量
     */
    @ExcelProperty(value = "销量")
    private Integer salesVolume;

    /**
     * 创建用户头像
     */
    private String createByAvatar;

}
