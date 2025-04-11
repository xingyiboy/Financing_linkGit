export interface ShoppingCartVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 规格库存编号
   */
  specificationId: string | number;

  /**
   * 购买数量
   */
  number: number;

}

export interface ShoppingCartForm extends BaseEntity {
  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 规格库存编号
   */
  specificationId?: string | number;

  /**
   * 购买数量
   */
  number?: number;

}

export interface ShoppingCartQuery extends PageQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 规格库存编号
   */
  specificationId?: string | number;

  /**
   * 购买数量
   */
  number?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



