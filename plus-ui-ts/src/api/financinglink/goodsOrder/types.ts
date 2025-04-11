export interface GoodsOrderVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 商品编号
   */
  goodsId: string | number;

  /**
   * 商品标题
   */
  title: string;

  /**
   * 商品首页图
   */
  homePicture: string;

  /**
   * 商品首页图Url
   */
  homePictureUrl: string;
  /**
   * 单价
   */
  univalence: number;

  /**
   * 购买数量
   */
  number: number;

  /**
   * 优惠价减少金额
   */
  discountMoney: number;

  /**
   * 花费金额
   */
  money: number;

  /**
   * 收货人姓名
   */
  name: string;

  /**
   * 收货人手机号
   */
  phone: string;

  /**
   * 地址编号
   */
  addressCode: string;

  /**
   * 详细地址
   */
  addressDetails: string;

  /**
   * 状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款
   */
  status: number;

  /**
   * 创建时间
   */
  createTime: string;

}

export interface GoodsOrderForm extends BaseEntity {
  /**
   * 商品标题
   */
  title?: string;

  /**
   * 商品首页图
   */
  homePicture?: string;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 购买数量
   */
  number?: number;

  /**
   * 优惠价减少金额
   */
  discountMoney?: number;

  /**
   * 花费金额
   */
  money?: number;

  /**
   * 收货人姓名
   */
  name?: string;

  /**
   * 收货人手机号
   */
  phone?: string;

  /**
   * 地址编号
   */
  addressCode?: string;

  /**
   * 详细地址
   */
  addressDetails?: string;

  /**
   * 状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款
   */
  status?: number;

}

export interface GoodsOrderQuery extends PageQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 商品编号
   */
  goodsId?: string | number;

  /**
   * 商品标题
   */
  title?: string;

  /**
   * 商品首页图
   */
  homePicture?: string;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 购买数量
   */
  number?: number;

  /**
   * 优惠价减少金额
   */
  discountMoney?: number;

  /**
   * 花费金额
   */
  money?: number;

  /**
   * 收货人姓名
   */
  name?: string;

  /**
   * 收货人手机号
   */
  phone?: string;

  /**
   * 地址编号
   */
  addressCode?: string;

  /**
   * 详细地址
   */
  addressDetails?: string;

  /**
   * 状态 0待付款 1已付款 2已发货 3等待自提 4已完成 5申请售后 6已退款
   */
  status?: number;

  /**
   * 创建时间
   */
  createTime?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



