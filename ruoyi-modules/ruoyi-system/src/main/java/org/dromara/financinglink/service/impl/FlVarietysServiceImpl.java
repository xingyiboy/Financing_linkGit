package org.dromara.financinglink.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlVarietysBo;
import org.dromara.financinglink.domain.vo.FlVarietysVo;
import org.dromara.financinglink.domain.FlVarietys;
import org.dromara.financinglink.mapper.FlVarietysMapper;
import org.dromara.financinglink.service.IFlVarietysService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 品种树Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@RequiredArgsConstructor
@Service
public class FlVarietysServiceImpl implements IFlVarietysService {

    private final FlVarietysMapper baseMapper;

    /**
     * 查询品种树
     *
     * @param id 主键
     * @return 品种树
     */
    @Override
    public FlVarietysVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }


    /**
     * 查询符合条件的品种树列表
     *
     * @param bo 查询条件
     * @return 品种树列表
     */
    @Override
    public List<FlVarietysVo> queryList(FlVarietysBo bo) {
        LambdaQueryWrapper<FlVarietys> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlVarietys> buildQueryWrapper(FlVarietysBo bo) {
        LambdaQueryWrapper<FlVarietys> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlVarietys::getId, bo.getId());
        lqw.eq(bo.getParentId() != null, FlVarietys::getParentId, bo.getParentId());
        lqw.eq(StringUtils.isNotBlank(bo.getLabel()), FlVarietys::getLabel, bo.getLabel());
        return lqw;
    }

    /**
     * 新增品种树
     *
     * @param bo 品种树
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlVarietysBo bo) {
        FlVarietys add = MapstructUtils.convert(bo, FlVarietys.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改品种树
     *
     * @param bo 品种树
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlVarietysBo bo) {
        FlVarietys update = MapstructUtils.convert(bo, FlVarietys.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlVarietys entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除品种树信息
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
