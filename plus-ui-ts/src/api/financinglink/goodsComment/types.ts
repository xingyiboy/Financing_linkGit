export interface GoodsCommentVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 商品编号
   */
  goodsId: string | number;

  /**
   * 评论内容
   */
  content: string;

  /**
   * 创建时间
   */
  createTime: string;

}

export interface GoodsCommentForm extends BaseEntity {
  /**
   * 评论内容
   */
  content?: string;

}

export interface GoodsCommentQuery extends PageQuery {

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 商品编号
   */
  goodsId?: string | number;

  /**
   * 评论内容
   */
  content?: string;

  /**
   * 创建时间
   */
  createTime?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



