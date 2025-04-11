package org.dromara.financinglink.domain.bo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlBankBo {

        // 银行的唯一标识
        private Long bankId;
        // 银行名称
        private String bankName;
        // 银行的介绍信息
        private String bankIntroduction;
        // 联系电话
        private String contactPhone;
        // 融资金额，使用BigDecimal来精确表示金额数值
        private BigDecimal financeAmount;
        // 年利率，使用BigDecimal来精确表示利率数值
        private BigDecimal annualInterestRate;
        // 还款期限，使用整数表示期限（具体单位可根据业务定义，如月、年等）
        private Integer repaymentPeriod;


    }



