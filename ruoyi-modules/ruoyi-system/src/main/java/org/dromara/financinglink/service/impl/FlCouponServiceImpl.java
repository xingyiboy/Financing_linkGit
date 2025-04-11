package org.dromara.financinglink.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlCouponBo;
import org.dromara.financinglink.domain.vo.FlCouponVo;
import org.dromara.financinglink.domain.FlCoupon;
import org.dromara.financinglink.mapper.FlCouponMapper;
import org.dromara.financinglink.service.IFlCouponService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠卷Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlCouponServiceImpl implements IFlCouponService {

    private final FlCouponMapper baseMapper;

    /**
     * 查询优惠卷
     *
     * @param userId 主键
     * @return 优惠卷
     */
    @Override
    public FlCouponVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询优惠卷列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷分页列表
     */
    @Override
    public TableDataInfo<FlCouponVo> queryPageList(FlCouponBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlCoupon> lqw = buildQueryWrapper(bo);
        Page<FlCouponVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠卷列表
     *
     * @param bo 查询条件
     * @return 优惠卷列表
     */
    @Override
    public List<FlCouponVo> queryList(FlCouponBo bo) {
        LambdaQueryWrapper<FlCoupon> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlCoupon> buildQueryWrapper(FlCouponBo bo) {
        LambdaQueryWrapper<FlCoupon> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FlCoupon::getUserId, bo.getUserId());
        lqw.eq(bo.getStartMoney() != null, FlCoupon::getStartMoney, bo.getStartMoney());
        lqw.eq(bo.getDiscountMoney() != null, FlCoupon::getDiscountMoney, bo.getDiscountMoney());
        lqw.eq(bo.getCreateTime() != null, FlCoupon::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getEndTime() != null, FlCoupon::getEndTime, bo.getEndTime());
        return lqw;
    }

    /**
     * 新增优惠卷
     *
     * @param bo 优惠卷
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlCouponBo bo) {
        FlCoupon add = MapstructUtils.convert(bo, FlCoupon.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改优惠卷
     *
     * @param bo 优惠卷
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlCouponBo bo) {
        FlCoupon update = MapstructUtils.convert(bo, FlCoupon.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlCoupon entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠卷信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
