package org.dromara.financinglink.service.impl;

import cn.hutool.json.JSONUtil;
import org.dromara.common.core.service.FlSpecificationService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlSpecificationBo;
import org.dromara.financinglink.domain.vo.FlSpecificationVo;
import org.dromara.financinglink.domain.FlSpecification;
import org.dromara.financinglink.mapper.FlSpecificationMapper;
import org.dromara.financinglink.service.IFlSpecificationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 规格库存Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlSpecificationServiceImpl implements IFlSpecificationService, FlSpecificationService {

    private final FlSpecificationMapper baseMapper;

    /**
     * 查询规格库存
     *
     * @param id 主键
     * @return 规格库存
     */
    @Override
    public FlSpecificationVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询规格库存列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 规格库存分页列表
     */
    @Override
    public TableDataInfo<FlSpecificationVo> queryPageList(FlSpecificationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlSpecification> lqw = buildQueryWrapper(bo);
        Page<FlSpecificationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的规格库存列表
     *
     * @param bo 查询条件
     * @return 规格库存列表
     */
    @Override
    public List<FlSpecificationVo> queryList(FlSpecificationBo bo) {
        LambdaQueryWrapper<FlSpecification> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlSpecification> buildQueryWrapper(FlSpecificationBo bo) {
        LambdaQueryWrapper<FlSpecification> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlSpecification::getId, bo.getId());
        lqw.eq(bo.getGoodsId() != null, FlSpecification::getGoodsId, bo.getGoodsId());
        lqw.eq(bo.getUnit() != null, FlSpecification::getUnit, bo.getUnit());
        lqw.eq(bo.getUnivalence() != null, FlSpecification::getUnivalence, bo.getUnivalence());
        lqw.eq(StringUtils.isNotBlank(bo.getHomePicture()), FlSpecification::getHomePicture, bo.getHomePicture());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FlSpecification::getContent, bo.getContent());
        lqw.eq(bo.getInventory() != null, FlSpecification::getInventory, bo.getInventory());
        return lqw;
    }

    /**
     * 新增规格库存
     *
     * @param bo 规格库存
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlSpecificationBo bo) {
        FlSpecification add = MapstructUtils.convert(bo, FlSpecification.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改规格库存
     *
     * @param bo 规格库存
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlSpecificationBo bo) {
        FlSpecification update = MapstructUtils.convert(bo, FlSpecification.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlSpecification entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除规格库存信息
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

    /**
     * 查询规格库存
     *
     * @param id 主键
     * @return 规格库存
     */
    @Override
    public String queryByIdTranslation(Long id) {
        FlSpecificationVo flSpecificationVo = baseMapper.selectVoById(id);
        return JsonUtils.toJsonString(flSpecificationVo);
    }
}
