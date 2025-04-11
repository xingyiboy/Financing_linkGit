package org.dromara.financinglink.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.financinglink.domain.vo.FlGoodsCommentVo;
import org.dromara.financinglink.domain.bo.FlGoodsCommentBo;
import org.dromara.financinglink.service.IFlGoodsCommentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品评价
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/goodsComment")
public class FlGoodsCommentController extends BaseController {

    private final IFlGoodsCommentService flGoodsCommentService;

    /**
     * 查询商品评价列表
     */
    @SaCheckPermission("financinglink:goodsComment:list")
    @GetMapping("/list")
    public TableDataInfo<FlGoodsCommentVo> list(FlGoodsCommentBo bo, PageQuery pageQuery) {
        return flGoodsCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品评价列表
     */
    @SaCheckPermission("financinglink:goodsComment:export")
    @Log(title = "商品评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlGoodsCommentBo bo, HttpServletResponse response) {
        List<FlGoodsCommentVo> list = flGoodsCommentService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品评价", FlGoodsCommentVo.class, response);
    }

    /**
     * 获取商品评价详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("financinglink:goodsComment:query")
    @GetMapping("/{userId}")
    public R<FlGoodsCommentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(flGoodsCommentService.queryById(userId));
    }

    /**
     * 新增商品评价
     */
    @SaCheckPermission("financinglink:goodsComment:add")
    @Log(title = "商品评价", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlGoodsCommentBo bo) {
        return toAjax(flGoodsCommentService.insertByBo(bo));
    }

    /**
     * 修改商品评价
     */
    @SaCheckPermission("financinglink:goodsComment:edit")
    @Log(title = "商品评价", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlGoodsCommentBo bo) {
        return toAjax(flGoodsCommentService.updateByBo(bo));
    }

    /**
     * 删除商品评价
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("financinglink:goodsComment:remove")
    @Log(title = "商品评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(flGoodsCommentService.deleteWithValidByIds(List.of(userIds), true));
    }
}
