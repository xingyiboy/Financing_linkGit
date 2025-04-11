export interface AgrKnowledgeVO {
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
   * 首页图片
   */
  homePicture: string;

  /**
   * 状态 0禁用 1启用
   */
  status: number;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 创建人id
   */
  createBy: number;

}

export interface AgrKnowledgeForm extends BaseEntity {
  /**
   * 标题
   */
  title?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 首页图片
   */
  homePicture?: string;

  /**
   * 状态 0禁用 1启用
   */
  status?: number;

}

export interface AgrKnowledgeQuery extends PageQuery {

  /**
   * 编号
   */
  id?: string | number;

  /**
   * 标题
   */
  title?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 首页图片
   */
  homePicture?: string;

  /**
   * 状态 0禁用 1启用
   */
  status?: number;

  /**
   * 创建时间
   */
  createTime?: string;

  /**
   * 创建人id
   */
  createBy?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



