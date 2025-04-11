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
import org.dromara.financinglink.domain.FlAppointment;
import org.dromara.financinglink.domain.FlSpecialist;
import org.dromara.financinglink.mapper.FlSpecialistMapper;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlQuestionsAnswersBo;
import org.dromara.financinglink.domain.vo.FlQuestionsAnswersVo;
import org.dromara.financinglink.domain.FlQuestionsAnswers;
import org.dromara.financinglink.mapper.FlQuestionsAnswersMapper;
import org.dromara.financinglink.service.IFlQuestionsAnswersService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 问答Service业务层处理
 *
 * @author xingyi
 * @date 2024-11-19
 */
@RequiredArgsConstructor
@Service
public class FlQuestionsAnswersServiceImpl implements IFlQuestionsAnswersService {

    private final FlQuestionsAnswersMapper baseMapper;
    private final FlSpecialistMapper flSpecialistMapper;

    /**
     * 查询问答
     *
     * @param id 主键
     * @return 问答
     */
    @Override
    public FlQuestionsAnswersVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询问答列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问答分页列表
     */
    @Override
    public TableDataInfo<FlQuestionsAnswersVo> queryPageList(FlQuestionsAnswersBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlQuestionsAnswers> lqw = buildQueryWrapper(bo);
        if(bo.getCreateBy()==null){

        }
        else if(bo.getCreateBy()!=1){
            //自己看自己
            lqw.eq(FlQuestionsAnswers::getCreateBy, LoginHelper.getUserId());
        }else{
            //专家看问题
            LambdaQueryWrapper<FlSpecialist> lqw2 = Wrappers.lambdaQuery();
            lqw2.eq(FlSpecialist::getUserId, LoginHelper.getUserId());
            FlSpecialist flSpecialist = flSpecialistMapper.selectOne(lqw2);
            lqw.eq(FlQuestionsAnswers::getSpecialist, flSpecialist.getId());
        }
        Page<FlQuestionsAnswersVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        //专家编号转为专家名称
        List<FlQuestionsAnswersVo> records = result.getRecords();
        for (FlQuestionsAnswersVo record : records) {
            LambdaQueryWrapper<FlSpecialist> fllqw = new LambdaQueryWrapper<>();
            fllqw.eq(FlSpecialist::getId, record.getSpecialist());
            FlSpecialist flSpecialist = flSpecialistMapper.selectOne(fllqw);
            if(flSpecialist!=null){
                record.setSpecialistNickname(flSpecialist.getName());
            }
        }
        result.setRecords(records);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的问答列表
     *
     * @param bo 查询条件
     * @return 问答列表
     */
    @Override
    public List<FlQuestionsAnswersVo> queryList(FlQuestionsAnswersBo bo) {
        LambdaQueryWrapper<FlQuestionsAnswers> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlQuestionsAnswers> buildQueryWrapper(FlQuestionsAnswersBo bo) {
        LambdaQueryWrapper<FlQuestionsAnswers> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FlQuestionsAnswers::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), FlQuestionsAnswers::getContent, bo.getContent());
        lqw.eq(bo.getStatus() != null, FlQuestionsAnswers::getStatus, bo.getStatus());
        lqw.like(StringUtils.isNotBlank(bo.getFlName()), FlQuestionsAnswers::getFlName, bo.getFlName());
        lqw.like(StringUtils.isNotBlank(bo.getAnswer()), FlQuestionsAnswers::getAnswer, bo.getAnswer());
        lqw.orderByDesc(FlQuestionsAnswers::getCreateTime);
        return lqw;
    }

    /**
     * 新增问答
     *
     * @param bo 问答
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlQuestionsAnswersBo bo) {
        FlQuestionsAnswers add = MapstructUtils.convert(bo, FlQuestionsAnswers.class);
        add.setStatus(0L);
        add.setCreateBy(LoginHelper.getUserId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改问答
     *
     * @param bo 问答
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlQuestionsAnswersBo bo) {
        FlQuestionsAnswers update = MapstructUtils.convert(bo, FlQuestionsAnswers.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlQuestionsAnswers entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除问答信息
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
