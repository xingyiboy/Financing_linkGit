package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlGoodsVo;
import org.dromara.financinglink.domain.bo.FlGoodsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品和需求Service接口
 *
 * @author Lion Li
 * @date 2024-10-15
 */
public interface IFlGoodsService {

    /**
     * 查询商品和需求
     *
     * @param id 主键
     * @return 商品和需求
     */
    FlGoodsVo queryById(Long id);

    /**
     * 分页查询商品和需求列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品和需求分页列表
     */
    TableDataInfo<FlGoodsVo> queryPageList(FlGoodsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品和需求列表
     *
     * @param bo 查询条件
     * @return 商品和需求列表
     */
    List<FlGoodsVo> queryList(FlGoodsBo bo);

    /**
     * 新增商品和需求
     *
     * @param bo 商品和需求
     * @return 是否新增成功
     */
    Boolean insertByBo(FlGoodsBo bo);

    /**
     * 修改商品和需求
     *
     * @param bo 商品和需求
     * @return 是否修改成功
     */
    Boolean updateByBo(FlGoodsBo bo);

    /**
     * 校验并批量删除商品和需求信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
