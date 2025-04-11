export interface CouponVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 起减价
   */
  startMoney: number;

  /**
   * 减少价格
   */
  discountMoney: number;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 结束时间
   */
  endTime: string;

}

export interface CouponForm extends BaseEntity {
  /**
   * 起减价
   */
  startMoney?: number;

  /**
   * 减少价格
   */
  discountMoney?: number;

}

export interface CouponQuery extends PageQuery {

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 起减价
   */
  startMoney?: number;

  /**
   * 减少价格
   */
  discountMoney?: number;

  /**
   * 创建时间
   */
  createTime?: string;

  /**
   * 结束时间
   */
  endTime?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



