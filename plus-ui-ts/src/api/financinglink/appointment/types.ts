export interface AppointmentVO {
  /**
   * 地址
   */
  address: string;

  /**
   * 种植作物
   */
  crop: string;

  /**
   * 种植作物详细信息
   */
  cropDetail: string;

  /**
   * 作物条件
   */
  cropCondition: string;

  /**
   * 土壤条件
   */
  edaphicCondition: string;

  /**
   * 面积
   */
  area: string;

  /**
   * 电话
   */
  phone: string;

  /**
   * 姓名
   */
  name: string;

}

export interface AppointmentForm extends BaseEntity {
  /**
   * 地址
   */
  address?: string;

  /**
   * 种植作物
   */
  crop?: string;

  /**
   * 种植作物详细信息
   */
  cropDetail?: string;

  /**
   * 作物条件
   */
  cropCondition?: string;

  /**
   * 土壤条件
   */
  edaphicCondition?: string;

  /**
   * 面积
   */
  area?: string;

  /**
   * 电话
   */
  phone?: string;

  /**
   * 姓名
   */
  name?: string;

}

export interface AppointmentQuery extends PageQuery {

  /**
   * 地址
   */
  address?: string;

  /**
   * 种植作物
   */
  crop?: string;

  /**
   * 电话
   */
  phone?: string;

  /**
   * 姓名
   */
  name?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



