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
import org.dromara.financinglink.domain.FlQuestionsAnswers;
import org.dromara.financinglink.domain.FlSpecialist;
import org.dromara.financinglink.mapper.FlSpecialistMapper;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlAppointmentBo;
import org.dromara.financinglink.domain.vo.FlAppointmentVo;
import org.dromara.financinglink.domain.FlAppointment;
import org.dromara.financinglink.mapper.FlAppointmentMapper;
import org.dromara.financinglink.service.IFlAppointmentService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 预约Service业务层处理
 *
 * @author xingyi
 * @date 2024-11-19
 */
@RequiredArgsConstructor
@Service
public class FlAppointmentServiceImpl implements IFlAppointmentService {

    private final FlAppointmentMapper baseMapper;
    private final FlSpecialistMapper flSpecialistMapper;

    /**
     * 查询预约
     *
     * @param id 主键
     * @return 预约
     */
    @Override
    public FlAppointmentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询预约列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 预约分页列表
     */
    @Override
    public TableDataInfo<FlAppointmentVo> queryPageList(FlAppointmentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlAppointment> lqw = buildQueryWrapper(bo);
        if(bo.getCreateBy()==null){

        }
        else if(bo.getCreateBy()!=1){
            //自己看自己
            lqw.eq(FlAppointment::getCreateBy, LoginHelper.getUserId());
        }else{
            //专家看预约
            LambdaQueryWrapper<FlSpecialist> lqw2 = Wrappers.lambdaQuery();
            lqw2.eq(FlSpecialist::getUserId, LoginHelper.getUserId());
            FlSpecialist flSpecialist = flSpecialistMapper.selectOne(lqw2);
            lqw.eq(FlAppointment::getSpecialist, flSpecialist.getId());
        }
        Page<FlAppointmentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的预约列表
     *
     * @param bo 查询条件
     * @return 预约列表
     */
    @Override
    public List<FlAppointmentVo> queryList(FlAppointmentBo bo) {
        LambdaQueryWrapper<FlAppointment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlAppointment> buildQueryWrapper(FlAppointmentBo bo) {
        LambdaQueryWrapper<FlAppointment> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAddress()), FlAppointment::getAddress, bo.getAddress());
        lqw.like(StringUtils.isNotBlank(bo.getCrop()), FlAppointment::getCrop, bo.getCrop());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), FlAppointment::getPhone, bo.getPhone());
        lqw.like(StringUtils.isNotBlank(bo.getName()), FlAppointment::getName, bo.getName());
        return lqw;
    }

    /**
     * 新增预约
     *
     * @param bo 预约
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlAppointmentBo bo) {
        FlAppointment add = MapstructUtils.convert(bo, FlAppointment.class);
        validEntityBeforeSave(add);
        add.setCreateBy(LoginHelper.getUserId());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改预约
     *
     * @param bo 预约
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlAppointmentBo bo) {
        FlAppointment update = MapstructUtils.convert(bo, FlAppointment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlAppointment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除预约信息
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
