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
import org.dromara.financinglink.domain.bo.FlSpecialistBo;
import org.dromara.financinglink.domain.vo.FlSpecialistVo;
import org.dromara.financinglink.domain.FlSpecialist;
import org.dromara.financinglink.mapper.FlSpecialistMapper;
import org.dromara.financinglink.service.IFlSpecialistService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 专家详情Service业务层处理
 *
 * @author xingyi
 * @date 2024-11-19
 */
@RequiredArgsConstructor
@Service
public class FlSpecialistServiceImpl implements IFlSpecialistService {

    private final FlSpecialistMapper baseMapper;

    /**
     * 查询专家详情
     *
     * @param id 主键
     * @return 专家详情
     */
    @Override
    public FlSpecialistVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询专家详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 专家详情分页列表
     */
    @Override
    public TableDataInfo<FlSpecialistVo> queryPageList(FlSpecialistBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlSpecialist> lqw = buildQueryWrapper(bo);
        Page<FlSpecialistVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的专家详情列表
     *
     * @param bo 查询条件
     * @return 专家详情列表
     */
    @Override
    public List<FlSpecialistVo> queryList(FlSpecialistBo bo) {
        LambdaQueryWrapper<FlSpecialist> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlSpecialist> buildQueryWrapper(FlSpecialistBo bo) {
        LambdaQueryWrapper<FlSpecialist> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), FlSpecialist::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getProfessional()), FlSpecialist::getProfessional, bo.getProfessional());
        lqw.like(StringUtils.isNotBlank(bo.getMajor()), FlSpecialist::getMajor, bo.getMajor());
        lqw.like(StringUtils.isNotBlank(bo.getPhone()), FlSpecialist::getPhone, bo.getPhone());
        lqw.like(StringUtils.isNotBlank(bo.getAddress()), FlSpecialist::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), FlSpecialist::getAvatar, bo.getAvatar());
        return lqw;
    }

    /**
     * 新增专家详情
     *
     * @param bo 专家详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlSpecialistBo bo) {
        FlSpecialist add = MapstructUtils.convert(bo, FlSpecialist.class);
        add.setUserId(LoginHelper.getUserId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改专家详情
     *
     * @param bo 专家详情
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlSpecialistBo bo) {
        FlSpecialist update = MapstructUtils.convert(bo, FlSpecialist.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlSpecialist entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除专家详情信息
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
     * 查询登录用户是否是专家,并获取专家信息
     *
     * @return
     */
    @Override
    public FlSpecialist isSpecialist() {
        LambdaQueryWrapper<FlSpecialist> lqw = Wrappers.lambdaQuery();
        lqw.eq(FlSpecialist::getUserId, LoginHelper.getUserId());
        FlSpecialist flSpecialist = baseMapper.selectOne(lqw);
        if(flSpecialist==null){
            return null;
        }
        return flSpecialist;
    }
}
