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
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlAgrKnowledgeBo;
import org.dromara.financinglink.domain.vo.FlAgrKnowledgeVo;
import org.dromara.financinglink.domain.FlAgrKnowledge;
import org.dromara.financinglink.mapper.FlAgrKnowledgeMapper;
import org.dromara.financinglink.service.IFlAgrKnowledgeService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 农业知识Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlAgrKnowledgeServiceImpl implements IFlAgrKnowledgeService {

    private final FlAgrKnowledgeMapper baseMapper;

    /**
     * 查询农业知识
     *
     * @param id 主键
     * @return 农业知识
     */
    @Override
    public FlAgrKnowledgeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询农业知识列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 农业知识分页列表
     */
    @Override
    public TableDataInfo<FlAgrKnowledgeVo> queryPageList(FlAgrKnowledgeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlAgrKnowledge> lqw = buildQueryWrapper(bo);
        // 不是外部调用，只能看自己创建的
        if(bo.getCreateBy()==null||bo.getCreateBy()!=1){
            lqw.eq(FlAgrKnowledge::getCreateBy, LoginHelper.getUserId());
        }else {
            lqw.eq(FlAgrKnowledge::getStatus, 1);
        }
        Page<FlAgrKnowledgeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的农业知识列表
     *
     * @param bo 查询条件
     * @return 农业知识列表
     */
    @Override
    public List<FlAgrKnowledgeVo> queryList(FlAgrKnowledgeBo bo) {
        LambdaQueryWrapper<FlAgrKnowledge> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlAgrKnowledge> buildQueryWrapper(FlAgrKnowledgeBo bo) {
        LambdaQueryWrapper<FlAgrKnowledge> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlAgrKnowledge::getId, bo.getId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FlAgrKnowledge::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), FlAgrKnowledge::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getHomePicture()), FlAgrKnowledge::getHomePicture, bo.getHomePicture());
        lqw.eq(bo.getStatus() != null, FlAgrKnowledge::getStatus, bo.getStatus());
        lqw.eq(bo.getCreateTime() != null, FlAgrKnowledge::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增农业知识
     *
     * @param bo 农业知识
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlAgrKnowledgeBo bo) {
        FlAgrKnowledge add = MapstructUtils.convert(bo, FlAgrKnowledge.class);
        add.setCreateBy(LoginHelper.getUserId());
        add.setCreateTime(new Date());
        add.setUpdateTime(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改农业知识
     *
     * @param bo 农业知识
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlAgrKnowledgeBo bo) {
        FlAgrKnowledge update = MapstructUtils.convert(bo, FlAgrKnowledge.class);
        update.setUpdateTime(new Date());
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlAgrKnowledge entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除农业知识信息
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
