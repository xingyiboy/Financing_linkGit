package org.dromara.financinglink.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.financinglink.domain.FlGoods;
import org.dromara.financinglink.domain.FlSpecification;
import org.dromara.financinglink.domain.bo.*;
import org.dromara.financinglink.domain.vo.*;
import org.dromara.financinglink.mapper.FlGoodsMapper;
import org.dromara.financinglink.mapper.FlSpecificationMapper;
import org.dromara.financinglink.service.*;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.service.ISysOssService;
import org.dromara.system.service.ISysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 外部接口(免登录)
 */

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/out")
public class OutController {

    private final IFlVarietysService flVarietysService;
    private final IFlGoodsService flGoodsService;
    private final ISysOssService ossService;
    private final ISysUserService sysUserService;
    private final IFlSpecificationService flSpecificationService;
    private final IFlAgrKnowledgeService flAgrKnowledgeService;
    private final IFlGoodsCommentService flGoodsCommentService;
    private final IFlAddressService flAddressService;
    private final IFlSpecialistService flSpecialistService;
    private final IFlAgrKnowCommentService flAgrKnowCommentService;
    private final IFlQuestionsAnswersService flQuestionsAnswersService;

    /**
     * 查询品种数列表
     * @param bo
     * @return
     */
    @GetMapping("/varietys/list")
    public R<List<FlVarietysVo>> list(FlVarietysBo bo) {
        List<FlVarietysVo> list = flVarietysService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 查询商品和需求列表
     */
    @GetMapping("/goods/list")
    public TableDataInfo<FlGoodsVo> list(FlGoodsBo bo, PageQuery pageQuery) {
        bo.setCreateBy(1L);
        return flGoodsService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取商品和需求详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<FlGoodsVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long id) {
        FlGoodsVo flGoodsVo = flGoodsService.queryById(id);
        SysUserVo sysUserVo = sysUserService.selectUserById(flGoodsVo.getCreateBy());
        if(sysUserVo.getAvatar()!=null){
            String createByAvatar  = ossService.selectUrlByIds(sysUserVo.getAvatar().toString());
            flGoodsVo.setCreateByAvatar(createByAvatar);
        }else {
            flGoodsVo.setCreateByAvatar("http://43.139.211.116:9000/financinglink/2024/10/20/d6d74dc587ab43289b754cdd5d365f21.jpg");
        }
        return R.ok(flGoodsVo);
    }
    /**
     * 获取商品规格信息
     *
     */
    @GetMapping("/flSpecification/{goodsId}")
    public R<List<FlSpecificationVo>> getFlSpecification(@NotNull(message = "主键不能为空")
                                @PathVariable Long goodsId) {
        FlSpecificationBo bo = new FlSpecificationBo();
        bo.setGoodsId(goodsId);
        List<FlSpecificationVo> flSpecificationVoList = flSpecificationService.queryList(bo);
        return R.ok(flSpecificationVoList);
    }

    /**
     * 查询农业知识列表
     */
    @GetMapping("/agrKnowledge/list")
    public TableDataInfo<FlAgrKnowledgeVo> list(FlAgrKnowledgeBo bo, PageQuery pageQuery) {
        bo.setCreateBy(1L);
        return flAgrKnowledgeService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询商品评价列表
     */
    @GetMapping("/goodsComment/list")
    public TableDataInfo<FlGoodsCommentVo> list(FlGoodsCommentBo bo, PageQuery pageQuery) {
        bo.setUserId(1L);
        return flGoodsCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询用户地址列表
     */
    @GetMapping("/address/list")
    public TableDataInfo<FlAddressVo> list(FlAddressBo bo, PageQuery pageQuery) {
        return flAddressService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取农业知识详细信息
     *
     * @param id 主键
     */
    @GetMapping("/agrKnowledge/{id}")
    public R<FlAgrKnowledgeVo> getInfo2(@NotNull(message = "主键不能为空")
                                       @PathVariable Long id) {
        return R.ok(flAgrKnowledgeService.queryById(id));
    }

    /**
     * 查询专家详情列表
     */
    @GetMapping("/specialist/list")
    public TableDataInfo<FlSpecialistVo> list(FlSpecialistBo bo, PageQuery pageQuery) {
        return flSpecialistService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询农业知识评价列表
     */
    @GetMapping("/agrKnowComment/list")
    public TableDataInfo<FlAgrKnowCommentVo> list(FlAgrKnowCommentBo bo, PageQuery pageQuery) {
        return flAgrKnowCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询问答列表
     */
    @GetMapping("/questionsAnswers/list")
    public TableDataInfo<FlQuestionsAnswersVo> list(FlQuestionsAnswersBo bo, PageQuery pageQuery) {
        return flQuestionsAnswersService.queryPageList(bo, pageQuery);
    }
}
