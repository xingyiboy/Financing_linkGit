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
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlAgrKnowCommentBo;
import org.dromara.financinglink.domain.vo.FlAgrKnowCommentVo;
import org.dromara.financinglink.domain.FlAgrKnowComment;
import org.dromara.financinglink.mapper.FlAgrKnowCommentMapper;
import org.dromara.financinglink.service.IFlAgrKnowCommentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 农业知识评价Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlAgrKnowCommentServiceImpl implements IFlAgrKnowCommentService {

    private final FlAgrKnowCommentMapper baseMapper;

    /**
     * 查询农业知识评价
     *
     * @param userId 主键
     * @return 农业知识评价
     */
    @Override
    public FlAgrKnowCommentVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询农业知识评价列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 农业知识评价分页列表
     */
    @Override
    public TableDataInfo<FlAgrKnowCommentVo> queryPageList(FlAgrKnowCommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlAgrKnowComment> lqw = buildQueryWrapper(bo);
        Page<FlAgrKnowCommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的农业知识评价列表
     *
     * @param bo 查询条件
     * @return 农业知识评价列表
     */
    @Override
    public List<FlAgrKnowCommentVo> queryList(FlAgrKnowCommentBo bo) {
        LambdaQueryWrapper<FlAgrKnowComment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlAgrKnowComment> buildQueryWrapper(FlAgrKnowCommentBo bo) {
        LambdaQueryWrapper<FlAgrKnowComment> lqw = Wrappers.lambdaQuery();
        lqw.like(bo.getUserId() != null, FlAgrKnowComment::getUserId, bo.getUserId());
        lqw.like(bo.getAgrKnowId() != null, FlAgrKnowComment::getAgrKnowId, bo.getAgrKnowId());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), FlAgrKnowComment::getContent, bo.getContent());
        lqw.eq(bo.getCreateTime() != null, FlAgrKnowComment::getCreateTime, bo.getCreateTime());
        lqw.orderByDesc(FlAgrKnowComment::getCreateTime);
        return lqw;
    }

    /**
     * 新增农业知识评价
     *
     * @param bo 农业知识评价
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlAgrKnowCommentBo bo) {
        FlAgrKnowComment add = MapstructUtils.convert(bo, FlAgrKnowComment.class);
        validEntityBeforeSave(add);
        add.setUserId(LoginHelper.getUserId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改农业知识评价
     *
     * @param bo 农业知识评价
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlAgrKnowCommentBo bo) {
        FlAgrKnowComment update = MapstructUtils.convert(bo, FlAgrKnowComment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlAgrKnowComment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除农业知识评价信息
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
