package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlGoodsOrderVo;
import org.dromara.financinglink.domain.bo.FlGoodsOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品订单Service接口
 *
 * @author Lion Li
 * @date 2024-10-20
 */
public interface IFlGoodsOrderService {

    /**
     * 查询商品订单
     *
     * @param id 主键
     * @return 商品订单
     */
    FlGoodsOrderVo queryById(Long id);

    /**
     * 分页查询商品订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品订单分页列表
     */
    TableDataInfo<FlGoodsOrderVo> queryPageList(FlGoodsOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品订单列表
     *
     * @param bo 查询条件
     * @return 商品订单列表
     */
    List<FlGoodsOrderVo> queryList(FlGoodsOrderBo bo);

    /**
     * 新增商品订单
     *
     * @param bo 商品订单
     * @return 是否新增成功
     */
    Boolean insertByBo(FlGoodsOrderBo bo);

    /**
     * 修改商品订单
     *
     * @param bo 商品订单
     * @return 是否修改成功
     */
    Boolean updateByBo(FlGoodsOrderBo bo);

    /**
     * 校验并批量删除商品订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
