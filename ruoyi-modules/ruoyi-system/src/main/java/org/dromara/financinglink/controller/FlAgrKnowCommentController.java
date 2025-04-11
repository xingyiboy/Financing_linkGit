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
import org.dromara.financinglink.domain.vo.FlAgrKnowCommentVo;
import org.dromara.financinglink.domain.bo.FlAgrKnowCommentBo;
import org.dromara.financinglink.service.IFlAgrKnowCommentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 农业知识评价
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/agrKnowComment")
public class FlAgrKnowCommentController extends BaseController {

    private final IFlAgrKnowCommentService flAgrKnowCommentService;

    /**
     * 查询农业知识评价列表
     */
    @SaCheckPermission("financinglink:agrKnowComment:list")
    @GetMapping("/list")
    public TableDataInfo<FlAgrKnowCommentVo> list(FlAgrKnowCommentBo bo, PageQuery pageQuery) {
        return flAgrKnowCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出农业知识评价列表
     */
    @SaCheckPermission("financinglink:agrKnowComment:export")
    @Log(title = "农业知识评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlAgrKnowCommentBo bo, HttpServletResponse response) {
        List<FlAgrKnowCommentVo> list = flAgrKnowCommentService.queryList(bo);
        ExcelUtil.exportExcel(list, "农业知识评价", FlAgrKnowCommentVo.class, response);
    }

    /**
     * 获取农业知识评价详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("financinglink:agrKnowComment:query")
    @GetMapping("/{userId}")
    public R<FlAgrKnowCommentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(flAgrKnowCommentService.queryById(userId));
    }

    /**
     * 新增农业知识评价
     */
    @SaCheckPermission("financinglink:agrKnowComment:add")
    @Log(title = "农业知识评价", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlAgrKnowCommentBo bo) {
        return toAjax(flAgrKnowCommentService.insertByBo(bo));
    }

    /**
     * 修改农业知识评价
     */
    @SaCheckPermission("financinglink:agrKnowComment:edit")
    @Log(title = "农业知识评价", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlAgrKnowCommentBo bo) {
        return toAjax(flAgrKnowCommentService.updateByBo(bo));
    }

    /**
     * 删除农业知识评价
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("financinglink:agrKnowComment:remove")
    @Log(title = "农业知识评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(flAgrKnowCommentService.deleteWithValidByIds(List.of(userIds), true));
    }
}
