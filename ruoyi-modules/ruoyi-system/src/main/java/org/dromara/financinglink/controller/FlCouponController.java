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
import org.dromara.financinglink.domain.vo.FlCouponVo;
import org.dromara.financinglink.domain.bo.FlCouponBo;
import org.dromara.financinglink.service.IFlCouponService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠卷
 *
 * @author Lion Li
 * @date 2024-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/coupon")
public class FlCouponController extends BaseController {

    private final IFlCouponService flCouponService;

    /**
     * 查询优惠卷列表
     */
    @SaCheckPermission("financinglink:coupon:list")
    @GetMapping("/list")
    public TableDataInfo<FlCouponVo> list(FlCouponBo bo, PageQuery pageQuery) {
        return flCouponService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠卷列表
     */
    @SaCheckPermission("financinglink:coupon:export")
    @Log(title = "优惠卷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlCouponBo bo, HttpServletResponse response) {
        List<FlCouponVo> list = flCouponService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠卷", FlCouponVo.class, response);
    }

    /**
     * 获取优惠卷详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("financinglink:coupon:query")
    @GetMapping("/{userId}")
    public R<FlCouponVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(flCouponService.queryById(userId));
    }

    /**
     * 新增优惠卷
     */
    @SaCheckPermission("financinglink:coupon:add")
    @Log(title = "优惠卷", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlCouponBo bo) {
        return toAjax(flCouponService.insertByBo(bo));
    }

    /**
     * 修改优惠卷
     */
    @SaCheckPermission("financinglink:coupon:edit")
    @Log(title = "优惠卷", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlCouponBo bo) {
        return toAjax(flCouponService.updateByBo(bo));
    }

    /**
     * 删除优惠卷
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("financinglink:coupon:remove")
    @Log(title = "优惠卷", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(flCouponService.deleteWithValidByIds(List.of(userIds), true));
    }
}
