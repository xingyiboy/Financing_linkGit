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
import org.dromara.financinglink.domain.bo.FlAddressBo;
import org.dromara.financinglink.domain.vo.FlAddressVo;
import org.dromara.financinglink.domain.FlAddress;
import org.dromara.financinglink.mapper.FlAddressMapper;
import org.dromara.financinglink.service.IFlAddressService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户地址Service业务层处理
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@RequiredArgsConstructor
@Service
public class FlAddressServiceImpl implements IFlAddressService {

    private final FlAddressMapper baseMapper;

    /**
     * 查询用户地址
     *
     * @param id 主键
     * @return 用户地址
     */
    @Override
    public FlAddressVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户地址列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户地址分页列表
     */
    @Override
    public TableDataInfo<FlAddressVo> queryPageList(FlAddressBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlAddress> lqw = buildQueryWrapper(bo);
        Page<FlAddressVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户地址列表
     *
     * @param bo 查询条件
     * @return 用户地址列表
     */
    @Override
    public List<FlAddressVo> queryList(FlAddressBo bo) {
        LambdaQueryWrapper<FlAddress> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlAddress> buildQueryWrapper(FlAddressBo bo) {
        LambdaQueryWrapper<FlAddress> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, FlAddress::getId, bo.getId());
        lqw.eq( FlAddress::getUserId, LoginHelper.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getAddressCode()), FlAddress::getAddressCode, bo.getAddressCode());
        lqw.eq(StringUtils.isNotBlank(bo.getAddressDetail()), FlAddress::getAddressDetail, bo.getAddressDetail());
        lqw.eq(bo.getIsDefault() != null, FlAddress::getIsDefault, bo.getIsDefault());
        lqw.like(StringUtils.isNotBlank(bo.getName()), FlAddress::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), FlAddress::getPhone, bo.getPhone());
        return lqw;
    }

    /**
     * 新增用户地址
     *
     * @param bo 用户地址
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlAddressBo bo) {
        FlAddress add = MapstructUtils.convert(bo, FlAddress.class);
        add.setUserId(LoginHelper.getUserId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户地址
     *
     * @param bo 用户地址
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlAddressBo bo) {
        FlAddress update = MapstructUtils.convert(bo, FlAddress.class);
        update.setUserId(LoginHelper.getUserId());
        if(update.getIsDefault() == 1){
            LambdaQueryWrapper<FlAddress> lqw = Wrappers.lambdaQuery();
            lqw.eq(FlAddress::getUserId, LoginHelper.getUserId());
            List<FlAddress> flAddresses = baseMapper.selectList(lqw);
            flAddresses.forEach(flAddress -> {
                flAddress.setIsDefault(0L);
            });
            baseMapper.updateBatchById(flAddresses);
        }
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlAddress entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户地址信息
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
