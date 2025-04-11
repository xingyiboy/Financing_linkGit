package org.dromara.financinglink.service.impl;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.financinglink.domain.FlSpecification;
import org.dromara.financinglink.mapper.FlSpecificationMapper;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlGoodsBo;
import org.dromara.financinglink.domain.vo.FlGoodsVo;
import org.dromara.financinglink.domain.FlGoods;
import org.dromara.financinglink.mapper.FlGoodsMapper;
import org.dromara.financinglink.service.IFlGoodsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品和需求Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@RequiredArgsConstructor
@Service
public class FlGoodsServiceImpl implements IFlGoodsService {

    private final FlGoodsMapper baseMapper;
    private final FlSpecificationMapper flSpecificationMapper;

    /**
     * 查询商品和需求
     *
     * @param id 主键
     * @return 商品和需求
     */
    @Override
    public FlGoodsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询商品和需求列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品和需求分页列表
     */
    @Override
    public TableDataInfo<FlGoodsVo> queryPageList(FlGoodsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlGoods> lqw = buildQueryWrapper(bo);
        //为1时是外部访问
        if(bo.getCreateBy()==null||bo.getCreateBy()!=1L){
            lqw.eq(FlGoods::getCreateBy, LoginHelper.getUserId());
        }else {
            lqw.eq(FlGoods::getStatus, 1);
        }
        Page<FlGoodsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品和需求列表
     *
     * @param bo 查询条件
     * @return 商品和需求列表
     */
    @Override
    public List<FlGoodsVo> queryList(FlGoodsBo bo) {
        LambdaQueryWrapper<FlGoods> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlGoods> buildQueryWrapper(FlGoodsBo bo) {
        LambdaQueryWrapper<FlGoods> lqw = Wrappers.lambdaQuery();
        lqw.eq(LoginHelper.getDeptId() != null, FlGoods::getDeptId, LoginHelper.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FlGoods::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), FlGoods::getContent, bo.getContent());
        lqw.like(bo.getUnivalence() != null, FlGoods::getUnivalence, bo.getUnivalence());
        lqw.eq(bo.getUnit() != null, FlGoods::getUnit, bo.getUnit());
        lqw.eq(bo.getMinimumPrice() != null, FlGoods::getMinimumPrice, bo.getMinimumPrice());
        lqw.eq(bo.getStatus() != null, FlGoods::getStatus, bo.getStatus());
        lqw.eq(bo.getType() != null, FlGoods::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getAddressDetail()), FlGoods::getAddressDetail, bo.getAddressDetail());
        lqw.like(StringUtils.isNotBlank(bo.getAddressCode()), FlGoods::getAddressCode, bo.getAddressCode());
        lqw.like(StringUtils.isNotBlank(bo.getLogistics()), FlGoods::getLogistics, bo.getLogistics());
        lqw.eq(bo.getVarietyFirst() != null, FlGoods::getVarietyFirst, bo.getVarietyFirst());
        lqw.eq(bo.getVarietySecond() != null, FlGoods::getVarietySecond, bo.getVarietySecond());
        lqw.eq(bo.getVarietyThird() != null, FlGoods::getVarietyThird, bo.getVarietyThird());
        //根据类型进行排序
        if(bo.getOrderByType()!=null) {
            switch (bo.getOrderByType()) {
                case 0:
                    lqw.orderByDesc(FlGoods::getUpdateTime);
                    break;
                case 1:
                    lqw.orderByAsc(FlGoods::getUpdateTime);
                    break;
                case 2:
                    lqw.orderByDesc(FlGoods::getUnivalence);
                    break;
                case 3:
                    lqw.orderByAsc(FlGoods::getUnivalence);
                    break;
                case 4:
                    lqw.orderByDesc(FlGoods::getSalesVolume);
                    break;
                case 5:
                    lqw.orderByAsc(FlGoods::getSalesVolume);
                    break;
            }
        }
        return lqw;
    }

    /**
     * 新增商品和需求
     *
     * @param bo 商品和需求
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlGoodsBo bo) {
        FlGoods add = MapstructUtils.convert(bo, FlGoods.class);
        validEntityBeforeSave(add);
        add.setSalesVolume(0);
        add.setCreateBy(LoginHelper.getUserId());
        add.setUpdateBy(LoginHelper.getUserId());
        add.setDeptId(LoginHelper.getDeptId());
        add.setStatus(0);
        boolean flag = baseMapper.insert(add) > 0;
        List<FlSpecification> specificationList = bo.getSpecificationList();
        //数据矫正
        for (FlSpecification flSpecification : specificationList) {
            flSpecification.setId(null);
            flSpecification.setGoodsId(add.getId());
        }
        flSpecificationMapper.insertBatch(specificationList);
        return flag;
    }

    /**
     * 修改商品和需求
     *
     * @param bo 商品和需求
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlGoodsBo bo) {
        FlGoods update = MapstructUtils.convert(bo, FlGoods.class);
        //判断是否有规格才给status启用
        if(update.getStatus()==1){
            LambdaQueryWrapper<FlSpecification> lqw = Wrappers.lambdaQuery();
            lqw.eq(FlSpecification::getGoodsId,update.getId());
            if(flSpecificationMapper.selectList(lqw).size()==0){
                throw new ServiceException("请先完成添加规格,再启用");
            }
        }
        validEntityBeforeSave(update);
        update.setUpdateBy(LoginHelper.getUserId());
        boolean flag = baseMapper.updateById(update) > 0;
        List<FlSpecification> specificationList = bo.getSpecificationList();
        //数据矫正
        //1.删除
        LambdaQueryWrapper<FlSpecification> lqw = Wrappers.lambdaQuery();
        lqw.eq(FlSpecification::getGoodsId,update.getId());
        flSpecificationMapper.delete(lqw);
        //2.矫正
        for (FlSpecification flSpecification : specificationList) {
            flSpecification.setId(null);
            flSpecification.setGoodsId(update.getId());
        }
        //3.插入
        flSpecificationMapper.insertBatch(specificationList);
        return flag;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlGoods entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品和需求信息
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
