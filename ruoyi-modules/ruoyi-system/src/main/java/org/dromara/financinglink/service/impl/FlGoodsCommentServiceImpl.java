package org.dromara.financinglink.service.impl;

import org.dromara.common.core.service.OssService;
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
import org.dromara.financinglink.domain.FlGoodsOrder;
import org.dromara.financinglink.mapper.FlGoodsOrderMapper;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.dromara.financinglink.domain.bo.FlGoodsCommentBo;
import org.dromara.financinglink.domain.vo.FlGoodsCommentVo;
import org.dromara.financinglink.domain.FlGoodsComment;
import org.dromara.financinglink.mapper.FlGoodsCommentMapper;
import org.dromara.financinglink.service.IFlGoodsCommentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品评价Service业务层处理
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@RequiredArgsConstructor
@Service
public class FlGoodsCommentServiceImpl implements IFlGoodsCommentService {

    private final FlGoodsCommentMapper baseMapper;
    private final FlGoodsOrderMapper flGoodsOrderMapper;
    private final ISysUserService sysUserService;
    private final OssService ossService;

    /**
     * 查询商品评价
     *
     * @param userId 主键
     * @return 商品评价
     */
    @Override
    public FlGoodsCommentVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询商品评价列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品评价分页列表
     */
    @Override
    public TableDataInfo<FlGoodsCommentVo> queryPageList(FlGoodsCommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlGoodsComment> lqw = buildQueryWrapper(bo);
        //为1时是外部访问
        if(bo.getUserId()==null||bo.getUserId()!=1L){
            lqw.eq(FlGoodsComment::getUserId, LoginHelper.getUserId());
        }
        Page<FlGoodsCommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<FlGoodsCommentVo> records = result.getRecords();
        //查询用户头像
        for (FlGoodsCommentVo record : records) {
            SysUserVo sysUserVo = sysUserService.selectUserById(record.getUserId());
            if(sysUserVo.getAvatar()!=null){
                String avatar  = ossService.selectUrlByIds(sysUserVo.getAvatar().toString());
                record.setAvatar(avatar);
            }else {
                record.setAvatar("http://43.139.211.116:9000/financinglink/2024/10/20/d6d74dc587ab43289b754cdd5d365f21.jpg");
            }
        }
        result.setRecords(records);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品评价列表
     *
     * @param bo 查询条件
     * @return 商品评价列表
     */
    @Override
    public List<FlGoodsCommentVo> queryList(FlGoodsCommentBo bo) {
        LambdaQueryWrapper<FlGoodsComment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlGoodsComment> buildQueryWrapper(FlGoodsCommentBo bo) {
        LambdaQueryWrapper<FlGoodsComment> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getGoodsId() != null, FlGoodsComment::getGoodsId, bo.getGoodsId());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), FlGoodsComment::getContent, bo.getContent());
        lqw.eq(bo.getCreateTime() != null, FlGoodsComment::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增商品评价
     *
     * @param bo 商品评价
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(FlGoodsCommentBo bo) {
        FlGoodsComment add = MapstructUtils.convert(bo, FlGoodsComment.class);
        validEntityBeforeSave(add);
        add.setUserId(LoginHelper.getUserId());
        add.setCreateTime(new Date());
        boolean flag = baseMapper.insert(add) > 0;
        FlGoodsOrder flGoodsOrder = flGoodsOrderMapper.selectById(bo.getOrderId());
        flGoodsOrder.setIsComment(1);
        flGoodsOrderMapper.updateById(flGoodsOrder);
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改商品评价
     *
     * @param bo 商品评价
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlGoodsCommentBo bo) {
        FlGoodsComment update = MapstructUtils.convert(bo, FlGoodsComment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlGoodsComment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品评价信息
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
