package org.dromara.financinglink.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.financinglink.domain.FlGoods;
import org.dromara.financinglink.domain.FlSpecification;
import org.dromara.financinglink.mapper.FlGoodsMapper;
import org.dromara.financinglink.mapper.FlSpecificationMapper;
import org.dromara.system.domain.SysUser;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlGoodsOrderBo;
import org.dromara.financinglink.domain.vo.FlGoodsOrderVo;
import org.dromara.financinglink.domain.FlGoodsOrder;
import org.dromara.financinglink.mapper.FlGoodsOrderMapper;
import org.dromara.financinglink.service.IFlGoodsOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品订单Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlGoodsOrderServiceImpl implements IFlGoodsOrderService {

    private final FlGoodsOrderMapper baseMapper;
    private final FlGoodsMapper flGoodsMapper;
    private final FlSpecificationMapper flSpecificationMapper;
    private final SysUserMapper sysUserMapper;

    /**
     * 查询商品订单
     *
     * @param id 主键
     * @return 商品订单
     */
    @Override
    public FlGoodsOrderVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询商品订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品订单分页列表
     */
    @Override
    public TableDataInfo<FlGoodsOrderVo> queryPageList(FlGoodsOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlGoodsOrder> lqw = buildQueryWrapper(bo);
        Page<FlGoodsOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品订单列表
     *
     * @param bo 查询条件
     * @return 商品订单列表
     */
    @Override
    public List<FlGoodsOrderVo> queryList(FlGoodsOrderBo bo) {
        LambdaQueryWrapper<FlGoodsOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlGoodsOrder> buildQueryWrapper(FlGoodsOrderBo bo) {
        LambdaQueryWrapper<FlGoodsOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlGoodsOrder::getId, bo.getId());
        if(bo.getType()==1){
            lqw.eq(FlGoodsOrder::getSellId, LoginHelper.getUserId());
        }else {
            lqw.eq(FlGoodsOrder::getUserId, LoginHelper.getUserId());
        }

        lqw.eq(bo.getGoodsId() != null, FlGoodsOrder::getGoodsId, bo.getGoodsId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FlGoodsOrder::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getHomePicture()), FlGoodsOrder::getHomePicture, bo.getHomePicture());
        lqw.eq(bo.getUnivalence() != null, FlGoodsOrder::getUnivalence, bo.getUnivalence());
        lqw.eq(bo.getNumber() != null, FlGoodsOrder::getNumber, bo.getNumber());
        lqw.eq(bo.getDiscountMoney() != null, FlGoodsOrder::getDiscountMoney, bo.getDiscountMoney());
        lqw.eq(bo.getMoney() != null, FlGoodsOrder::getMoney, bo.getMoney());
        lqw.like(StringUtils.isNotBlank(bo.getName()), FlGoodsOrder::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getPhone()), FlGoodsOrder::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getAddressCode()), FlGoodsOrder::getAddressCode, bo.getAddressCode());
        lqw.like(StringUtils.isNotBlank(bo.getAddressDetails()), FlGoodsOrder::getAddressDetails, bo.getAddressDetails());
        lqw.eq(bo.getStatus() != null, FlGoodsOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getCreateTime() != null, FlGoodsOrder::getCreateTime, bo.getCreateTime());
        lqw.orderByDesc(FlGoodsOrder::getCreateTime);
        return lqw;
    }

    /**
     * 新增商品订单
     *
     * @param bo 商品订单
     * @return 是否新增成功
     */
    @Override
    @Transactional //事务
    public Boolean insertByBo(FlGoodsOrderBo bo) {
        FlGoodsOrder add = MapstructUtils.convert(bo, FlGoodsOrder.class);
        add.setIsComment(0);
        FlGoods flGoods = flGoodsMapper.selectById(add.getGoodsId());
        //更新商品销量
        flGoods.setSalesVolume(flGoods.getSalesVolume()+bo.getNumber().intValue());
        flGoodsMapper.updateById(flGoods);
        //更新规格库存数量
        FlSpecification flSpecification = flSpecificationMapper.selectById(bo.getSpecId());
        if(flSpecification.getInventory()-bo.getNumber()<0){
            throw new RuntimeException("库存不足");
        }
        flSpecification.setInventory(flSpecification.getInventory()-bo.getNumber());
        flSpecificationMapper.updateById(flSpecification);
        //更新购买人金额
        SysUser sysUser = sysUserMapper.selectById(bo.getUserId());
        if(sysUser!=null){
            BigDecimal subtract = sysUser.getMoney().subtract(bo.getMoney());
            if(subtract.compareTo(BigDecimal.ZERO)<0){
                throw new RuntimeException("金额不足");
            }
            sysUser.setMoney(subtract);
        }
        sysUserMapper.updateById(sysUser);
        //设置售出人
        add.setSellId(flGoods.getCreateBy());
        validEntityBeforeSave(add);
        add.setUserId(LoginHelper.getUserId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改商品订单
     *
     * @param bo 商品订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlGoodsOrderBo bo) {
        FlGoodsOrder update = MapstructUtils.convert(bo, FlGoodsOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlGoodsOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品订单信息
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
