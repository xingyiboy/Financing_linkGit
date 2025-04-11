package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.vo.FlAppointmentVo;
import org.dromara.financinglink.domain.bo.FlAppointmentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 预约Service接口
 *
 * @author xingyi
 * @date 2024-11-19
 */
public interface IFlAppointmentService {

    /**
     * 查询预约
     *
     * @param id 主键
     * @return 预约
     */
    FlAppointmentVo queryById(Long id);

    /**
     * 分页查询预约列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 预约分页列表
     */
    TableDataInfo<FlAppointmentVo> queryPageList(FlAppointmentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的预约列表
     *
     * @param bo 查询条件
     * @return 预约列表
     */
    List<FlAppointmentVo> queryList(FlAppointmentBo bo);

    /**
     * 新增预约
     *
     * @param bo 预约
     * @return 是否新增成功
     */
    Boolean insertByBo(FlAppointmentBo bo);

    /**
     * 修改预约
     *
     * @param bo 预约
     * @return 是否修改成功
     */
    Boolean updateByBo(FlAppointmentBo bo);

    /**
     * 校验并批量删除预约信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
