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
import org.dromara.financinglink.domain.vo.FlAgrKnowledgeVo;
import org.dromara.financinglink.domain.bo.FlAgrKnowledgeBo;
import org.dromara.financinglink.service.IFlAgrKnowledgeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 农业知识
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/agrKnowledge")
public class FlAgrKnowledgeController extends BaseController {

    private final IFlAgrKnowledgeService flAgrKnowledgeService;

    /**
     * 查询农业知识列表
     */
    @SaCheckPermission("financinglink:agrKnowledge:list")
    @GetMapping("/list")
    public TableDataInfo<FlAgrKnowledgeVo> list(FlAgrKnowledgeBo bo, PageQuery pageQuery) {
        return flAgrKnowledgeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出农业知识列表
     */
    @SaCheckPermission("financinglink:agrKnowledge:export")
    @Log(title = "农业知识", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlAgrKnowledgeBo bo, HttpServletResponse response) {
        List<FlAgrKnowledgeVo> list = flAgrKnowledgeService.queryList(bo);
        ExcelUtil.exportExcel(list, "农业知识", FlAgrKnowledgeVo.class, response);
    }

    /**
     * 获取农业知识详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:agrKnowledge:query")
    @GetMapping("/{id}")
    public R<FlAgrKnowledgeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flAgrKnowledgeService.queryById(id));
    }

    /**
     * 新增农业知识
     */
    @SaCheckPermission("financinglink:agrKnowledge:add")
    @Log(title = "农业知识", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlAgrKnowledgeBo bo) {
        return toAjax(flAgrKnowledgeService.insertByBo(bo));
    }

    /**
     * 修改农业知识
     */
    @SaCheckPermission("financinglink:agrKnowledge:edit")
    @Log(title = "农业知识", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlAgrKnowledgeBo bo) {
        return toAjax(flAgrKnowledgeService.updateByBo(bo));
    }

    /**
     * 删除农业知识
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:agrKnowledge:remove")
    @Log(title = "农业知识", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flAgrKnowledgeService.deleteWithValidByIds(List.of(ids), true));
    }
}
