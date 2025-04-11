export interface SpecialistVO {
  /**
   * 编号
   */
  id: string | number;

  /**
   * 用户编号
   */
  userId: string | number;

  /**
   * 专家姓名
   */
  name: string;

  /**
   * 职称
   */
  professional: string;

  /**
   * 专业
   */
  major: string;

  /**
   * 电话
   */
  phone: string;

  /**
   * 单位
   */
  address: string;

  /**
   * 头像
   */
  avatar: string;

  /**
   * 头像Url
   */
  avatarUrl: string;
}

export interface SpecialistForm extends BaseEntity {
  /**
   * 专家姓名
   */
  name?: string;

  /**
   * 职称
   */
  professional?: string;

  /**
   * 专业
   */
  major?: string;

  /**
   * 电话
   */
  phone?: string;

  /**
   * 单位
   */
  address?: string;

  /**
   * 头像
   */
  avatar?: string;

}

export interface SpecialistQuery extends PageQuery {

  /**
   * 专家姓名
   */
  name?: string;

  /**
   * 职称
   */
  professional?: string;

  /**
   * 专业
   */
  major?: string;

  /**
   * 电话
   */
  phone?: string;

  /**
   * 单位
   */
  address?: string;

  /**
   * 头像
   */
  avatar?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



