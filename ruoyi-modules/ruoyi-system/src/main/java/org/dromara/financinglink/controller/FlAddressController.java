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
import org.dromara.financinglink.domain.vo.FlAddressVo;
import org.dromara.financinglink.domain.bo.FlAddressBo;
import org.dromara.financinglink.service.IFlAddressService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户地址
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/address")
public class FlAddressController extends BaseController {

    private final IFlAddressService flAddressService;

    /**
     * 查询用户地址列表
     */
    @SaCheckPermission("financinglink:address:list")
    @GetMapping("/list")
    public TableDataInfo<FlAddressVo> list(FlAddressBo bo, PageQuery pageQuery) {
        return flAddressService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户地址列表
     */
    @SaCheckPermission("financinglink:address:export")
    @Log(title = "用户地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlAddressBo bo, HttpServletResponse response) {
        List<FlAddressVo> list = flAddressService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户地址", FlAddressVo.class, response);
    }

    /**
     * 获取用户地址详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:address:query")
    @GetMapping("/{id}")
    public R<FlAddressVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flAddressService.queryById(id));
    }

    /**
     * 新增用户地址
     */
    @SaCheckPermission("financinglink:address:add")
    @Log(title = "用户地址", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlAddressBo bo) {
        return toAjax(flAddressService.insertByBo(bo));
    }

    /**
     * 修改用户地址
     */
    @SaCheckPermission("financinglink:address:edit")
    @Log(title = "用户地址", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlAddressBo bo) {
        return toAjax(flAddressService.updateByBo(bo));
    }

    /**
     * 删除用户地址
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:address:remove")
    @Log(title = "用户地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flAddressService.deleteWithValidByIds(List.of(ids), true));
    }
}
