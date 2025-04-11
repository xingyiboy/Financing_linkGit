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
import org.dromara.financinglink.domain.vo.FlVarietysVo;
import org.dromara.financinglink.domain.bo.FlVarietysBo;
import org.dromara.financinglink.service.IFlVarietysService;

/**
 * 品种树
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/varietys")
public class FlVarietysController extends BaseController {

    private final IFlVarietysService flVarietysService;

    /**
     * 查询品种树列表
     */
    @SaCheckPermission("financinglink:varietys:list")
    @GetMapping("/list")
    public R<List<FlVarietysVo>> list(FlVarietysBo bo) {
        List<FlVarietysVo> list = flVarietysService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出品种树列表
     */
    @SaCheckPermission("financinglink:varietys:export")
    @Log(title = "品种树", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlVarietysBo bo, HttpServletResponse response) {
        List<FlVarietysVo> list = flVarietysService.queryList(bo);
        ExcelUtil.exportExcel(list, "品种树", FlVarietysVo.class, response);
    }

    /**
     * 获取品种树详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:varietys:query")
    @GetMapping("/{id}")
    public R<FlVarietysVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flVarietysService.queryById(id));
    }

    /**
     * 新增品种树
     */
    @SaCheckPermission("financinglink:varietys:add")
    @Log(title = "品种树", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlVarietysBo bo) {
        return toAjax(flVarietysService.insertByBo(bo));
    }

    /**
     * 修改品种树
     */
    @SaCheckPermission("financinglink:varietys:edit")
    @Log(title = "品种树", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlVarietysBo bo) {
        return toAjax(flVarietysService.updateByBo(bo));
    }

    /**
     * 删除品种树
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:varietys:remove")
    @Log(title = "品种树", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flVarietysService.deleteWithValidByIds(List.of(ids), true));
    }
}
