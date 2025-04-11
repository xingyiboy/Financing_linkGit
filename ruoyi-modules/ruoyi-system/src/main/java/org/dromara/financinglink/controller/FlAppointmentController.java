package org.dromara.financinglink.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
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
import org.dromara.financinglink.domain.vo.FlAppointmentVo;
import org.dromara.financinglink.domain.bo.FlAppointmentBo;
import org.dromara.financinglink.service.IFlAppointmentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 预约
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/appointment")
public class FlAppointmentController extends BaseController {

    private final IFlAppointmentService flAppointmentService;

    /**
     * 查询预约列表
     */
    @SaCheckPermission("financinglink:appointment:list")
    @GetMapping("/list")
    public TableDataInfo<FlAppointmentVo> list(FlAppointmentBo bo, PageQuery pageQuery) {
        if(LoginHelper.getUserId()!=1L){
            bo.setCreateBy(LoginHelper.getUserId());
        }
        return flAppointmentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出预约列表
     */
    @SaCheckPermission("financinglink:appointment:export")
    @Log(title = "预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlAppointmentBo bo, HttpServletResponse response) {
        List<FlAppointmentVo> list = flAppointmentService.queryList(bo);
        ExcelUtil.exportExcel(list, "预约", FlAppointmentVo.class, response);
    }

    /**
     * 获取预约详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:appointment:query")
    @GetMapping("/{id}")
    public R<FlAppointmentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flAppointmentService.queryById(id));
    }

    /**
     * 新增预约
     */
    @SaCheckPermission("financinglink:appointment:add")
    @Log(title = "预约", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlAppointmentBo bo) {
        return toAjax(flAppointmentService.insertByBo(bo));
    }

    /**
     * 修改预约
     */
    @SaCheckPermission("financinglink:appointment:edit")
    @Log(title = "预约", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlAppointmentBo bo) {
        return toAjax(flAppointmentService.updateByBo(bo));
    }

    /**
     * 删除预约
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:appointment:remove")
    @Log(title = "预约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flAppointmentService.deleteWithValidByIds(List.of(ids), true));
    }
}
