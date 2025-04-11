export interface AgrKnowCommentVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 农业知识编号
   */
  agrKnowId: string | number;

  /**
   * 评论内容
   */
  content: string;

  /**
   * 创建时间
   */
  createTime: string;

}

export interface AgrKnowCommentForm extends BaseEntity {
  /**
   * 评论内容
   */
  content?: string;

}

export interface AgrKnowCommentQuery extends PageQuery {

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 农业知识编号
   */
  agrKnowId?: string | number;

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



