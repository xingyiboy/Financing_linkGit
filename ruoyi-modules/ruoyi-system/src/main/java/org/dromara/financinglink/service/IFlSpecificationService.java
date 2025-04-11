package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlSpecificationVo;
import org.dromara.financinglink.domain.bo.FlSpecificationBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 规格库存Service接口
 *
 * @author Lion Li
 * @date 2024-10-20
 */
public interface IFlSpecificationService {

    /**
     * 查询规格库存
     *
     * @param id 主键
     * @return 规格库存
     */
    FlSpecificationVo queryById(Long id);

    /**
     * 分页查询规格库存列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 规格库存分页列表
     */
    TableDataInfo<FlSpecificationVo> queryPageList(FlSpecificationBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的规格库存列表
     *
     * @param bo 查询条件
     * @return 规格库存列表
     */
    List<FlSpecificationVo> queryList(FlSpecificationBo bo);

    /**
     * 新增规格库存
     *
     * @param bo 规格库存
     * @return 是否新增成功
     */
    Boolean insertByBo(FlSpecificationBo bo);

    /**
     * 修改规格库存
     *
     * @param bo 规格库存
     * @return 是否修改成功
     */
    Boolean updateByBo(FlSpecificationBo bo);

    /**
     * 校验并批量删除规格库存信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
