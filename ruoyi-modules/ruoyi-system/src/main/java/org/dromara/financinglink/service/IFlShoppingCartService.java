package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlShoppingCartVo;
import org.dromara.financinglink.domain.bo.FlShoppingCartBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 购物车Service接口
 *
 * @author Lion Li
 * @date 2024-11-12
 */
public interface IFlShoppingCartService {

    /**
     * 查询购物车
     *
     * @param id 主键
     * @return 购物车
     */
    FlShoppingCartVo queryById(Long id);

    /**
     * 分页查询购物车列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 购物车分页列表
     */
    TableDataInfo<FlShoppingCartVo> queryPageList(FlShoppingCartBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的购物车列表
     *
     * @param bo 查询条件
     * @return 购物车列表
     */
    List<FlShoppingCartVo> queryList(FlShoppingCartBo bo);

    /**
     * 新增购物车
     *
     * @param bo 购物车
     * @return 是否新增成功
     */
    Boolean insertByBo(FlShoppingCartBo bo);

    /**
     * 修改购物车
     *
     * @param bo 购物车
     * @return 是否修改成功
     */
    Boolean updateByBo(FlShoppingCartBo bo);

    /**
     * 校验并批量删除购物车信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
