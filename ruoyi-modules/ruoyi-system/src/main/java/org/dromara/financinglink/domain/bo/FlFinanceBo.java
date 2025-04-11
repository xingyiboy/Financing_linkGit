package org.dromara.financinglink.domain.bo;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlFinanceBo {
    // 融资记录的唯一标识
    private Long id;
    // 关联的银行id
    private Long bankId;
    // 用户名
    private String username;
    // 真实姓名
    private String realName;
    // 融资状态
    private String status;
    // 备注信息
    private String remark;
    // 融资金额，不能为空，使用BigDecimal来精确表示金额数值
    @NotNull(message = "融资金额不能为空")
    private BigDecimal financeAmount;
    // 年利率，不能为空，使用BigDecimal来精确表示利率数值
    @NotNull(message = "年利率不能为空")
    private BigDecimal annualInterestRate;
    // 还款期限，不能为空，使用整数表示期限（具体单位可根据业务定义，如月、年等）
    @NotNull(message = "还款期限不能为空")
    private Integer repaymentPeriod;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;

    // 以下是自动生成的getter和setter方法，使用Lombok的@Data注解后会自动生成，这里仅为示意完整代码结构列出
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(BigDecimal financeAmount) {
        this.financeAmount = financeAmount;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public Integer getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(Integer repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
