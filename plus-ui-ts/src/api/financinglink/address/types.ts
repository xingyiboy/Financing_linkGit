export interface AddressVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 地址编码
   */
  addressCode: string;

  /**
   * 详细地址
   */
  addressDetail: string;

  /**
   * 是否是默认地址 0不是 1是
   */
  isDefault: number;

  /**
   * 收货人姓名
   */
  name: string;

  /**
   * 收货人手机号
   */
  phone: string;

}

export interface AddressForm extends BaseEntity {
  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 地址编码
   */
  addressCode?: string;

  /**
   * 详细地址
   */
  addressDetail?: string;

  /**
   * 是否是默认地址 0不是 1是
   */
  isDefault?: number;

  /**
   * 收货人姓名
   */
  name?: string;

  /**
   * 收货人手机号
   */
  phone?: string;

}

export interface AddressQuery extends PageQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 地址编码
   */
  addressCode?: string;

  /**
   * 详细地址
   */
  addressDetail?: string;

  /**
   * 是否是默认地址 0不是 1是
   */
  isDefault?: number;

  /**
   * 收货人姓名
   */
  name?: string;

  /**
   * 收货人手机号
   */
  phone?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



