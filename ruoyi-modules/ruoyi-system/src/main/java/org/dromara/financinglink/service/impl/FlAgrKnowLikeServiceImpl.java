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
import org.dromara.financinglink.domain.bo.FlAgrKnowLikeBo;
import org.dromara.financinglink.domain.vo.FlAgrKnowLikeVo;
import org.dromara.financinglink.domain.FlAgrKnowLike;
import org.dromara.financinglink.mapper.FlAgrKnowLikeMapper;
import org.dromara.financinglink.service.IFlAgrKnowLikeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 农业知识点赞Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlAgrKnowLikeServiceImpl implements IFlAgrKnowLikeService {

    private final FlAgrKnowLikeMapper baseMapper;

    /**
     * 查询农业知识点赞
     *
     * @param userId 主键
     * @return 农业知识点赞
     */
    @Override
    public FlAgrKnowLikeVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询农业知识点赞列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 农业知识点赞分页列表
     */
    @Override
    public TableDataInfo<FlAgrKnowLikeVo> queryPageList(FlAgrKnowLikeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlAgrKnowLike> lqw = buildQueryWrapper(bo);
        Page<FlAgrKnowLikeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的农业知识点赞列表
     *
     * @param bo 查询条件
     * @return 农业知识点赞列表
     */
    @Override
    public List<FlAgrKnowLikeVo> queryList(FlAgrKnowLikeBo bo) {
        LambdaQueryWrapper<FlAgrKnowLike> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlAgrKnowLike> buildQueryWrapper(FlAgrKnowLikeBo bo) {
        LambdaQueryWrapper<FlAgrKnowLike> lqw = Wrappers.lambdaQuery();
        lqw.like(bo.getUserId() != null, FlAgrKnowLike::getUserId, bo.getUserId());
        lqw.like(bo.getAgrKnowId() != null, FlAgrKnowLike::getAgrKnowId, bo.getAgrKnowId());
        return lqw;
    }

    /**
     * 新增农业知识点赞
     *
     * @param bo 农业知识点赞
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlAgrKnowLikeBo bo) {
        FlAgrKnowLike add = MapstructUtils.convert(bo, FlAgrKnowLike.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改农业知识点赞
     *
     * @param bo 农业知识点赞
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlAgrKnowLikeBo bo) {
        FlAgrKnowLike update = MapstructUtils.convert(bo, FlAgrKnowLike.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlAgrKnowLike entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除农业知识点赞信息
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
