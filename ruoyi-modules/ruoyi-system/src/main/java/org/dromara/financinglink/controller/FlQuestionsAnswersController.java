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
import org.dromara.financinglink.domain.vo.FlQuestionsAnswersVo;
import org.dromara.financinglink.domain.bo.FlQuestionsAnswersBo;
import org.dromara.financinglink.service.IFlQuestionsAnswersService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 问答
 *
 * @author xingyi
 * @date 2024-11-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/financinglink/questionsAnswers")
public class FlQuestionsAnswersController extends BaseController {

    private final IFlQuestionsAnswersService flQuestionsAnswersService;

    /**
     * 查询问答列表
     */
    @SaCheckPermission("financinglink:questionsAnswers:list")
    @GetMapping("/list")
    public TableDataInfo<FlQuestionsAnswersVo> list(FlQuestionsAnswersBo bo, PageQuery pageQuery) {
        if(LoginHelper.getUserId()!=1L){
            bo.setCreateBy(LoginHelper.getUserId());
        }

        return flQuestionsAnswersService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出问答列表
     */
    @SaCheckPermission("financinglink:questionsAnswers:export")
    @Log(title = "问答", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlQuestionsAnswersBo bo, HttpServletResponse response) {
        List<FlQuestionsAnswersVo> list = flQuestionsAnswersService.queryList(bo);
        ExcelUtil.exportExcel(list, "问答", FlQuestionsAnswersVo.class, response);
    }

    /**
     * 获取问答详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("financinglink:questionsAnswers:query")
    @GetMapping("/{id}")
    public R<FlQuestionsAnswersVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flQuestionsAnswersService.queryById(id));
    }

    /**
     * 新增问答
     */
    @SaCheckPermission("financinglink:questionsAnswers:add")
    @Log(title = "问答", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlQuestionsAnswersBo bo) {
        return toAjax(flQuestionsAnswersService.insertByBo(bo));
    }

    /**
     * 修改问答
     */
    @SaCheckPermission("financinglink:questionsAnswers:edit")
    @Log(title = "问答", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlQuestionsAnswersBo bo) {
        return toAjax(flQuestionsAnswersService.updateByBo(bo));
    }

    /**
     * 删除问答
     *
     * @param ids 主键串
     */
    @SaCheckPermission("financinglink:questionsAnswers:remove")
    @Log(title = "问答", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flQuestionsAnswersService.deleteWithValidByIds(List.of(ids), true));
    }
}
