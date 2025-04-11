package org.dromara.financinglink.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.financinglink.domain.FlSpecialist;
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
import org.dromara.financinglink.domain.vo.FlSpecialistVo;
import org.dromara.financinglink.domain.bo.FlSpecialistBo;
import org.dromara.financinglink.service.IFlSpecialistService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 专家详情
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/specialist")
public class FlSpecialistController extends BaseController {

    private final IFlSpecialistService flSpecialistService;

    /**
     * 查询专家详情列表
     */
    @SaCheckPermission("financinglink:specialist:list")
    @GetMapping("/list")
    public TableDataInfo<FlSpecialistVo> list(FlSpecialistBo bo, PageQuery pageQuery) {
        return flSpecialistService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出专家详情列表
     */
    @SaCheckPermission("financinglink:specialist:export")
    @Log(title = "专家详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlSpecialistBo bo, HttpServletResponse response) {
        List<FlSpecialistVo> list = flSpecialistService.queryList(bo);
        ExcelUtil.exportExcel(list, "专家详情", FlSpecialistVo.class, response);
    }

    /**
     * 获取专家详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:specialist:query")
    @GetMapping("/{id}")
    public R<FlSpecialistVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flSpecialistService.queryById(id));
    }

    /**
     * 新增专家详情
     */
    @SaCheckPermission("financinglink:specialist:add")
    @Log(title = "专家详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlSpecialistBo bo) {
        return toAjax(flSpecialistService.insertByBo(bo));
    }

    /**
     * 修改专家详情
     */
    @SaCheckPermission("financinglink:specialist:edit")
    @Log(title = "专家详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlSpecialistBo bo) {
        return toAjax(flSpecialistService.updateByBo(bo));
    }

    /**
     * 删除专家详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:specialist:remove")
    @Log(title = "专家详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flSpecialistService.deleteWithValidByIds(List.of(ids), true));
    }
    /**
     * 查询登录用户是否是专家
     *
     */
    @SaCheckPermission("financinglink:specialist:query")
    @GetMapping("/isSpecialist")
    public R<FlSpecialist> isSpecialist() {
        return R.ok(flSpecialistService.isSpecialist());
    }
}
