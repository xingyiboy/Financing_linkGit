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
import org.dromara.financinglink.domain.vo.FlGoodsVo;
import org.dromara.financinglink.domain.bo.FlGoodsBo;
import org.dromara.financinglink.service.IFlGoodsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品和需求
 *
 * @author Lion Li
 * @date 2024-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/goods")
public class FlGoodsController extends BaseController {

    private final IFlGoodsService flGoodsService;

    /**
     * 查询商品和需求列表
     */
    @SaCheckPermission("financinglink:goods:list")
    @GetMapping("/list")
    public TableDataInfo<FlGoodsVo> list(FlGoodsBo bo, PageQuery pageQuery) {
        return flGoodsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品和需求列表
     */
    @SaCheckPermission("financinglink:goods:export")
    @Log(title = "商品和需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlGoodsBo bo, HttpServletResponse response) {
        List<FlGoodsVo> list = flGoodsService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品和需求", FlGoodsVo.class, response);
    }

    /**
     * 获取商品和需求详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:goods:query")
    @GetMapping("/{id}")
    public R<FlGoodsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flGoodsService.queryById(id));
    }

    /**
     * 新增商品和需求
     */
    @SaCheckPermission("financinglink:goods:add")
    @Log(title = "商品和需求", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlGoodsBo bo) {
        return toAjax(flGoodsService.insertByBo(bo));
    }

    /**
     * 修改商品和需求
     */
    @SaCheckPermission("financinglink:goods:edit")
    @Log(title = "商品和需求", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlGoodsBo bo) {
        return toAjax(flGoodsService.updateByBo(bo));
    }

    /**
     * 删除商品和需求
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:goods:remove")
    @Log(title = "商品和需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flGoodsService.deleteWithValidByIds(List.of(ids), true));
    }
}
