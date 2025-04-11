package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlAddressVo;
import org.dromara.financinglink.domain.bo.FlAddressBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户地址Service接口
 *
 * @author Lion Li
 * @date 2024-11-12
 */
public interface IFlAddressService {

    /**
     * 查询用户地址
     *
     * @param id 主键
     * @return 用户地址
     */
    FlAddressVo queryById(Long id);

    /**
     * 分页查询用户地址列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户地址分页列表
     */
    TableDataInfo<FlAddressVo> queryPageList(FlAddressBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户地址列表
     *
     * @param bo 查询条件
     * @return 用户地址列表
     */
    List<FlAddressVo> queryList(FlAddressBo bo);

    /**
     * 新增用户地址
     *
     * @param bo 用户地址
     * @return 是否新增成功
     */
    Boolean insertByBo(FlAddressBo bo);

    /**
     * 修改用户地址
     *
     * @param bo 用户地址
     * @return 是否修改成功
     */
    Boolean updateByBo(FlAddressBo bo);

    /**
     * 校验并批量删除用户地址信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
