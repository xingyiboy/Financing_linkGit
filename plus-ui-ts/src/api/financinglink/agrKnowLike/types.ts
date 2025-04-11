export interface AgrKnowLikeVO {
  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 农业知识编号
   */
  agrKnowId: string | number;

}

export interface AgrKnowLikeForm extends BaseEntity {
}

export interface AgrKnowLikeQuery extends PageQuery {

  /**
   * 用户编号
   */
  userId?: string | number;

  /**
   * 农业知识编号
   */
  agrKnowId?: string | number;

    /**
     * 日期范围参数
     */
    params?: any;
}



