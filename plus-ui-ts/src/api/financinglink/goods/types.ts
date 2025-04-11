export interface GoodsVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 标题
   */
  title: string;

  /**
   * 详细内容
   */
  content: string;

  /**
   * 首页图片
   */
  homePicture: string;

  /**
   * 首页图片Url
   */
  homePictureUrl: string;
  /**
   * 品种
   */
  variety: number;

  /**
   * 图片
   */
  picture: string;

  /**
   * 图片Url
   */
  pictureUrl: string;
  /**
   * 单价
   */
  univalence: number;

  /**
   * 单价对应的单位
   */
  unit: number;

  /**
   * 起售价(元)
   */
  minimumPrice: number;

  /**
   * 状态 0禁用 1启用
   */
  status: number;

  /**
   * 类型 0需求 1销售
   */
  type: number;

  /**
   * 发货详细地址
   */
  addressDetail: string;

  /**
   * 地址编码
   */
  addressCode: string | [];

  /**
   * 物流方式
   */
  logistics: string;

  /**
   * 创建者
   */
  createBy: number;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 更新者
   */
  updateBy: number;

  /**
   * 更新时间
   */
  updateTime: string;
}

export interface GoodsForm extends BaseEntity {
  /**
   * 标题
   */
  title?: string;

  /**
   * 详细内容
   */
  content?: string;

  /**
   * 首页图片
   */
  homePicture?: string;

  /**
   * 品种
   */
  variety?: number;

  /**
   * 图片
   */
  picture?: string;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 单价对应的单位
   */
  unit?: number;

  /**
   * 起售价(元)
   */
  minimumPrice?: number;

  /**
   * 状态 0禁用 1启用
   */
  status?: number;

  /**
   * 类型 0需求 1销售
   */
  type?: number;

  /**
   * 发货详细地址
   */
  addressDetail?: string;

  /**
   * 地址编码
   */
  addressCode?: string;

  /**
   * 物流方式
   */
  logistics?: string;
}

export interface GoodsQuery extends PageQuery {
  /**
   * 标题
   */
  title?: string;

  /**
   * 详细内容
   */
  content?: string;

  /**
   * 首页图片
   */
  homePicture?: string;

  /**
   * 品种
   */
  variety?: number;

  /**
   * 图片
   */
  picture?: string;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 单价对应的单位
   */
  unit?: number;

  /**
   * 起售价(元)
   */
  minimumPrice?: number;

  /**
   * 状态 0禁用 1启用
   */
  status?: number;

  /**
   * 类型 0需求 1销售
   */
  type?: number;

  /**
   * 发货详细地址
   */
  addressDetail?: string;

  /**
   * 地址编码
   */
  addressCode?: string;

  /**
   * 物流方式
   */
  logistics?: string;

  /**
   * 创建者
   */
  createBy?: number;

  /**
   * 创建时间
   */
  createTime?: string;

  /**
   * 日期范围参数
   */
  params?: any;
  varietyFirst ?:any
  varietySecond ?:any
  varietyThird ?:any
  
}
