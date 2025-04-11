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
import org.dromara.financinglink.domain.vo.FlAgrKnowLikeVo;
import org.dromara.financinglink.domain.bo.FlAgrKnowLikeBo;
import org.dromara.financinglink.service.IFlAgrKnowLikeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 农业知识点赞
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/agrKnowLike")
public class FlAgrKnowLikeController extends BaseController {

    private final IFlAgrKnowLikeService flAgrKnowLikeService;

    /**
     * 查询农业知识点赞列表
     */
    @SaCheckPermission("financinglink:agrKnowLike:list")
    @GetMapping("/list")
    public TableDataInfo<FlAgrKnowLikeVo> list(FlAgrKnowLikeBo bo, PageQuery pageQuery) {
        return flAgrKnowLikeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出农业知识点赞列表
     */
    @SaCheckPermission("financinglink:agrKnowLike:export")
    @Log(title = "农业知识点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlAgrKnowLikeBo bo, HttpServletResponse response) {
        List<FlAgrKnowLikeVo> list = flAgrKnowLikeService.queryList(bo);
        ExcelUtil.exportExcel(list, "农业知识点赞", FlAgrKnowLikeVo.class, response);
    }

    /**
     * 获取农业知识点赞详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("financinglink:agrKnowLike:query")
    @GetMapping("/{userId}")
    public R<FlAgrKnowLikeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(flAgrKnowLikeService.queryById(userId));
    }

    /**
     * 新增农业知识点赞
     */
    @SaCheckPermission("financinglink:agrKnowLike:add")
    @Log(title = "农业知识点赞", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlAgrKnowLikeBo bo) {
        return toAjax(flAgrKnowLikeService.insertByBo(bo));
    }

    /**
     * 修改农业知识点赞
     */
    @SaCheckPermission("financinglink:agrKnowLike:edit")
    @Log(title = "农业知识点赞", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlAgrKnowLikeBo bo) {
        return toAjax(flAgrKnowLikeService.updateByBo(bo));
    }

    /**
     * 删除农业知识点赞
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("financinglink:agrKnowLike:remove")
    @Log(title = "农业知识点赞", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(flAgrKnowLikeService.deleteWithValidByIds(List.of(userIds), true));
    }
}
