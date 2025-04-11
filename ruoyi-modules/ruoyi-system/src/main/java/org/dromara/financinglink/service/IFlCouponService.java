package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlCouponVo;
import org.dromara.financinglink.domain.bo.FlCouponBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠卷Service接口
 *
 * @author Lion Li
 * @date 2024-10-20
 */
public interface IFlCouponService {

    /**
     * 查询优惠卷
     *
     * @param userId 主键
     * @return 优惠卷
     */
    FlCouponVo queryById(Long userId);

    /**
     * 分页查询优惠卷列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷分页列表
     */
    TableDataInfo<FlCouponVo> queryPageList(FlCouponBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的优惠卷列表
     *
     * @param bo 查询条件
     * @return 优惠卷列表
     */
    List<FlCouponVo> queryList(FlCouponBo bo);

    /**
     * 新增优惠卷
     *
     * @param bo 优惠卷
     * @return 是否新增成功
     */
    Boolean insertByBo(FlCouponBo bo);

    /**
     * 修改优惠卷
     *
     * @param bo 优惠卷
     * @return 是否修改成功
     */
    Boolean updateByBo(FlCouponBo bo);

    /**
     * 校验并批量删除优惠卷信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
