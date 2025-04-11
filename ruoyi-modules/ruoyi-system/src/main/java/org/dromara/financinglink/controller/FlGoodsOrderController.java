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
import org.dromara.financinglink.domain.vo.FlGoodsOrderVo;
import org.dromara.financinglink.domain.bo.FlGoodsOrderBo;
import org.dromara.financinglink.service.IFlGoodsOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品订单
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/goodsOrder")
public class FlGoodsOrderController extends BaseController {

    private final IFlGoodsOrderService flGoodsOrderService;

    /**
     * 查询商品订单列表
     */
    @SaCheckPermission("financinglink:goodsOrder:list")
    @GetMapping("/list")
    public TableDataInfo<FlGoodsOrderVo> list(FlGoodsOrderBo bo, PageQuery pageQuery) {
        return flGoodsOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品订单列表
     */
    @SaCheckPermission("financinglink:goodsOrder:export")
    @Log(title = "商品订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlGoodsOrderBo bo, HttpServletResponse response) {
        List<FlGoodsOrderVo> list = flGoodsOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品订单", FlGoodsOrderVo.class, response);
    }

    /**
     * 获取商品订单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:goodsOrder:query")
    @GetMapping("/{id}")
    public R<FlGoodsOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flGoodsOrderService.queryById(id));
    }

    /**
     * 新增商品订单
     */
    @SaCheckPermission("financinglink:goodsOrder:add")
    @Log(title = "商品订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlGoodsOrderBo bo) {
        return toAjax(flGoodsOrderService.insertByBo(bo));
    }

    /**
     * 修改商品订单
     */
    @SaCheckPermission("financinglink:goodsOrder:edit")
    @Log(title = "商品订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlGoodsOrderBo bo) {
        return toAjax(flGoodsOrderService.updateByBo(bo));
    }

    /**
     * 删除商品订单
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:goodsOrder:remove")
    @Log(title = "商品订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flGoodsOrderService.deleteWithValidByIds(List.of(ids), true));
    }
}
