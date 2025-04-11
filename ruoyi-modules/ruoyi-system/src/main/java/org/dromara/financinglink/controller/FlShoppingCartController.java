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
import org.dromara.financinglink.domain.vo.FlShoppingCartVo;
import org.dromara.financinglink.domain.bo.FlShoppingCartBo;
import org.dromara.financinglink.service.IFlShoppingCartService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 购物车
 *
 * @author Lion Li
 * @date 2024-11-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/shoppingCart")
public class FlShoppingCartController extends BaseController {

    private final IFlShoppingCartService flShoppingCartService;

    /**
     * 查询购物车列表
     */
    @SaCheckPermission("financinglink:shoppingCart:list")
    @GetMapping("/list")
    public TableDataInfo<FlShoppingCartVo> list(FlShoppingCartBo bo, PageQuery pageQuery) {
        return flShoppingCartService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出购物车列表
     */
    @SaCheckPermission("financinglink:shoppingCart:export")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlShoppingCartBo bo, HttpServletResponse response) {
        List<FlShoppingCartVo> list = flShoppingCartService.queryList(bo);
        ExcelUtil.exportExcel(list, "购物车", FlShoppingCartVo.class, response);
    }

    /**
     * 获取购物车详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:shoppingCart:query")
    @GetMapping("/{id}")
    public R<FlShoppingCartVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flShoppingCartService.queryById(id));
    }

    /**
     * 新增购物车
     */
    @SaCheckPermission("financinglink:shoppingCart:add")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlShoppingCartBo bo) {
        return toAjax(flShoppingCartService.insertByBo(bo));
    }

    /**
     * 修改购物车
     */
    @SaCheckPermission("financinglink:shoppingCart:edit")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlShoppingCartBo bo) {
        return toAjax(flShoppingCartService.updateByBo(bo));
    }

    /**
     * 删除购物车
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:shoppingCart:remove")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flShoppingCartService.deleteWithValidByIds(List.of(ids), true));
    }
}
