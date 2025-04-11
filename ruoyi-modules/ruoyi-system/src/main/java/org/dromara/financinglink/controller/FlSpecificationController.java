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
import org.dromara.financinglink.domain.vo.FlSpecificationVo;
import org.dromara.financinglink.domain.bo.FlSpecificationBo;
import org.dromara.financinglink.service.IFlSpecificationService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 规格库存
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/specification")
public class FlSpecificationController extends BaseController {

    private final IFlSpecificationService flSpecificationService;

    /**
     * 查询规格库存列表
     */
    @SaCheckPermission("financinglink:specification:list")
    @GetMapping("/list")
    public TableDataInfo<FlSpecificationVo> list(FlSpecificationBo bo, PageQuery pageQuery) {
        return flSpecificationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出规格库存列表
     */
    @SaCheckPermission("financinglink:specification:export")
    @Log(title = "规格库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlSpecificationBo bo, HttpServletResponse response) {
        List<FlSpecificationVo> list = flSpecificationService.queryList(bo);
        ExcelUtil.exportExcel(list, "规格库存", FlSpecificationVo.class, response);
    }

    /**
     * 获取规格库存详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:specification:query")
    @GetMapping("/{id}")
    public R<FlSpecificationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flSpecificationService.queryById(id));
    }

    /**
     * 新增规格库存
     */
    @SaCheckPermission("financinglink:specification:add")
    @Log(title = "规格库存", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlSpecificationBo bo) {
        return toAjax(flSpecificationService.insertByBo(bo));
    }

    /**
     * 修改规格库存
     */
    @SaCheckPermission("financinglink:specification:edit")
    @Log(title = "规格库存", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlSpecificationBo bo) {
        return toAjax(flSpecificationService.updateByBo(bo));
    }

    /**
     * 删除规格库存
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:specification:remove")
    @Log(title = "规格库存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flSpecificationService.deleteWithValidByIds(List.of(ids), true));
    }
}
