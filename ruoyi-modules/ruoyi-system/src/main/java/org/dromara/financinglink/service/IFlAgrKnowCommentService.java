package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlAgrKnowCommentVo;
import org.dromara.financinglink.domain.bo.FlAgrKnowCommentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 农业知识评价Service接口
 *
 * @author Lion Li
 * @date 2024-10-20
 */
public interface IFlAgrKnowCommentService {

    /**
     * 查询农业知识评价
     *
     * @param userId 主键
     * @return 农业知识评价
     */
    FlAgrKnowCommentVo queryById(Long userId);

    /**
     * 分页查询农业知识评价列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 农业知识评价分页列表
     */
    TableDataInfo<FlAgrKnowCommentVo> queryPageList(FlAgrKnowCommentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的农业知识评价列表
     *
     * @param bo 查询条件
     * @return 农业知识评价列表
     */
    List<FlAgrKnowCommentVo> queryList(FlAgrKnowCommentBo bo);

    /**
     * 新增农业知识评价
     *
     * @param bo 农业知识评价
     * @return 是否新增成功
     */
    Boolean insertByBo(FlAgrKnowCommentBo bo);

    /**
     * 修改农业知识评价
     *
     * @param bo 农业知识评价
     * @return 是否修改成功
     */
    Boolean updateByBo(FlAgrKnowCommentBo bo);

    /**
     * 校验并批量删除农业知识评价信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
