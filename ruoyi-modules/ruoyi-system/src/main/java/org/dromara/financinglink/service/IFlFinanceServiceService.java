package org.dromara.financinglink.service;

import org.dromara.financinglink.domain.bo.FlFinanceBo;
public interface IFlFinanceServiceService {

    // 定义添加融资的方法，接收一个FlFinanceBo实体对象作为参数，无返回值
    void add(FlFinanceBo finance);

}
