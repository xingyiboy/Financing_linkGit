package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlQuestionsAnswersVo;
import org.dromara.financinglink.domain.bo.FlQuestionsAnswersBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 问答Service接口
 *
 * @author xingyi
 * @date 2024-11-19
 */
public interface IFlQuestionsAnswersService {

    /**
     * 查询问答
     *
     * @param id 主键
     * @return 问答
     */
    FlQuestionsAnswersVo queryById(Long id);

    /**
     * 分页查询问答列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 问答分页列表
     */
    TableDataInfo<FlQuestionsAnswersVo> queryPageList(FlQuestionsAnswersBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的问答列表
     *
     * @param bo 查询条件
     * @return 问答列表
     */
    List<FlQuestionsAnswersVo> queryList(FlQuestionsAnswersBo bo);

    /**
     * 新增问答
     *
     * @param bo 问答
     * @return 是否新增成功
     */
    Boolean insertByBo(FlQuestionsAnswersBo bo);

    /**
     * 修改问答
     *
     * @param bo 问答
     * @return 是否修改成功
     */
    Boolean updateByBo(FlQuestionsAnswersBo bo);

    /**
     * 校验并批量删除问答信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
