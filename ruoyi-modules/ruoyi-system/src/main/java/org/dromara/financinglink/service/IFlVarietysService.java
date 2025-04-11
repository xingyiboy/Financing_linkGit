package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlVarietysVo;
import org.dromara.financinglink.domain.bo.FlVarietysBo;

import java.util.Collection;
import java.util.List;

/**
 * 品种树Service接口
 *
 * @author Lion Li
 * @date 2024-10-15
 */
public interface IFlVarietysService {

    /**
     * 查询品种树
     *
     * @param id 主键
     * @return 品种树
     */
    FlVarietysVo queryById(Long id);


    /**
     * 查询符合条件的品种树列表
     *
     * @param bo 查询条件
     * @return 品种树列表
     */
    List<FlVarietysVo> queryList(FlVarietysBo bo);

    /**
     * 新增品种树
     *
     * @param bo 品种树
     * @return 是否新增成功
     */
    Boolean insertByBo(FlVarietysBo bo);

    /**
     * 修改品种树
     *
     * @param bo 品种树
     * @return 是否修改成功
     */
    Boolean updateByBo(FlVarietysBo bo);

    /**
     * 校验并批量删除品种树信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
