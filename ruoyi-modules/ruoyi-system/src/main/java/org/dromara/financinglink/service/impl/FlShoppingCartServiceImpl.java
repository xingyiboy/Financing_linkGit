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
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlShoppingCartBo;
import org.dromara.financinglink.domain.vo.FlShoppingCartVo;
import org.dromara.financinglink.domain.FlShoppingCart;
import org.dromara.financinglink.mapper.FlShoppingCartMapper;
import org.dromara.financinglink.service.IFlShoppingCartService;

import java.util.*;

/**
 * 购物车Service业务层处理
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@RequiredArgsConstructor
@Service
public class FlShoppingCartServiceImpl implements IFlShoppingCartService {

    private final FlShoppingCartMapper baseMapper;
    private final FlGoodsMapper flGoodsMapper;
    private final FlSpecificationMapper flSpecificationMapper;

    /**
     * 查询购物车
     *
     * @param id 主键
     * @return 购物车
     */
    @Override
    public FlShoppingCartVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询购物车列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 购物车分页列表
     */
    @Override
    public TableDataInfo<FlShoppingCartVo> queryPageList(FlShoppingCartBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlShoppingCart> lqw = buildQueryWrapper(bo);
        Page<FlShoppingCartVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<FlShoppingCartVo> records = result.getRecords();
        List<FlShoppingCartVo> newOne = new ArrayList<>();
        for (FlShoppingCartVo record : records) {
            FlSpecification flSpecification = flSpecificationMapper.selectById(record.getSpecificationId());
            FlGoods flGoods = flGoodsMapper.selectById(flSpecification.getGoodsId());
            if(flGoods!=null){
                //设置商品信息 若查无数据则忽略此购物车记录
                record.setGoodsVo(flGoods);
                newOne.add(record);
            }
        }
        result.setRecords(newOne);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的购物车列表
     *
     * @param bo 查询条件
     * @return 购物车列表
     */
    @Override
    public List<FlShoppingCartVo> queryList(FlShoppingCartBo bo) {
        LambdaQueryWrapper<FlShoppingCart> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlShoppingCart> buildQueryWrapper(FlShoppingCartBo bo) {
        LambdaQueryWrapper<FlShoppingCart> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlShoppingCart::getId, bo.getId());
        lqw.eq( FlShoppingCart::getUserId, LoginHelper.getUserId());
        lqw.eq(bo.getSpecificationId() != null, FlShoppingCart::getSpecificationId, bo.getSpecificationId());
        lqw.eq(bo.getNumber() != null, FlShoppingCart::getNumber, bo.getNumber());
        return lqw;
    }

    /**
     * 新增购物车
     *
     * @param bo 购物车
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlShoppingCartBo bo) {
        FlShoppingCart add = MapstructUtils.convert(bo, FlShoppingCart.class);
        validEntityBeforeSave(add);
        add.setUserId(LoginHelper.getUserId());
        add.setUpdateTime(new Date());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改购物车
     *
     * @param bo 购物车
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlShoppingCartBo bo) {
        FlShoppingCart update = MapstructUtils.convert(bo, FlShoppingCart.class);
        update.setUpdateTime(new Date());
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlShoppingCart entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除购物车信息
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
