package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.FlSpecialist;
import org.dromara.financinglink.domain.vo.FlSpecialistVo;
import org.dromara.financinglink.domain.bo.FlSpecialistBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 专家详情Service接口
 *
 * @author xingyi
 * @date 2024-11-19
 */
public interface IFlSpecialistService {

    /**
     * 查询专家详情
     *
     * @param id 主键
     * @return 专家详情
     */
    FlSpecialistVo queryById(Long id);

    /**
     * 分页查询专家详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 专家详情分页列表
     */
    TableDataInfo<FlSpecialistVo> queryPageList(FlSpecialistBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的专家详情列表
     *
     * @param bo 查询条件
     * @return 专家详情列表
     */
    List<FlSpecialistVo> queryList(FlSpecialistBo bo);

    /**
     * 新增专家详情
     *
     * @param bo 专家详情
     * @return 是否新增成功
     */
    Boolean insertByBo(FlSpecialistBo bo);

    /**
     * 修改专家详情
     *
     * @param bo 专家详情
     * @return 是否修改成功
     */
    Boolean updateByBo(FlSpecialistBo bo);

    /**
     * 校验并批量删除专家详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询登录用户是否是专家
     * @return
     */
    FlSpecialist isSpecialist();
}
