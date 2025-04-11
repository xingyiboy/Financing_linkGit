export interface SpecificationVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 商品编号
   */
  goodsId: string | number;

  /**
   * 对应的单位
   */
  unit: number;

  /**
   * 单价
   */
  univalence: number;

  /**
   * 展示图片
   */
  homePicture: string;

  /**
   * 展示图片Url
   */
  homePictureUrl: string;
  /**
   * 说明
   */
  content: string;

  /**
   * 库存
   */
  inventory: number;

}

export interface SpecificationForm extends BaseEntity {
  /**
   * 商品编号
   */
  goodsId?: string | number;

  /**
   * 对应的单位
   */
  unit?: number;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 展示图片
   */
  homePicture?: string;

  /**
   * 说明
   */
  content?: string;

  /**
   * 库存
   */
  inventory?: number;

}

export interface SpecificationQuery extends PageQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 商品编号
   */
  goodsId?: string | number;

  /**
   * 对应的单位
   */
  unit?: number;

  /**
   * 单价
   */
  univalence?: number;

  /**
   * 展示图片
   */
  homePicture?: string;

  /**
   * 说明
   */
  content?: string;

  /**
   * 库存
   */
  inventory?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



