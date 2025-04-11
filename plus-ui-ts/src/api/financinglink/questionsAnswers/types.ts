export interface QuestionsAnswersVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 标题
   */
  title: string;

  /**
   * 内容
   */
  content: string;

  /**
   * 状态
   */
  status: number;

  /**
   * 农作物名称
   */
  flName: string;

  /**
   * 回答
   */
  answer: string;

}

export interface QuestionsAnswersForm extends BaseEntity {
  /**
   * 标题
   */
  title?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 状态
   */
  status?: number;

  /**
   * 农作物名称
   */
  flName?: string;

  /**
   * 回答
   */
  answer?: string;

}

export interface QuestionsAnswersQuery extends PageQuery {

  /**
   * 标题
   */
  title?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 状态
   */
  status?: number;

  /**
   * 农作物名称
   */
  flName?: string;

  /**
   * 回答
   */
  answer?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



